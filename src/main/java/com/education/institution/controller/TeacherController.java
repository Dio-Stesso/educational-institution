package com.education.institution.controller;

import com.education.institution.model.Teacher;
import com.education.institution.model.dto.request.TeacherRequestDto;
import com.education.institution.model.dto.response.TeacherResponseDto;
import com.education.institution.service.StudentService;
import com.education.institution.service.TeacherService;
import com.education.institution.service.mapper.RequestDtoMapper;
import com.education.institution.service.mapper.ResponseDtoMapper;
import com.education.institution.util.SortUtil;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherService teacherService;
    private final StudentService studentService;
    private final ResponseDtoMapper<TeacherResponseDto, Teacher> responseDtoMapper;
    private final RequestDtoMapper<TeacherRequestDto, Teacher> requestDtoMapper;

    public TeacherController(TeacherService teacherService,
                             StudentService studentService,
                             ResponseDtoMapper<TeacherResponseDto, Teacher> responseDtoMapper,
                             RequestDtoMapper<TeacherRequestDto, Teacher> requestDtoMapper) {
        this.teacherService = teacherService;
        this.studentService = studentService;
        this.responseDtoMapper = responseDtoMapper;
        this.requestDtoMapper = requestDtoMapper;
    }

    @PostMapping
    @ApiOperation("Create new teacher.")
    public TeacherResponseDto create(@RequestBody @Valid TeacherRequestDto requestDto) {
        Teacher teacher = teacherService.save(requestDtoMapper.toModel(requestDto));
        return responseDtoMapper.toDto(teacher);
    }

    @PostMapping("/{id}")
    @ApiOperation("Changing an existing teacher.")
    public TeacherResponseDto set(@PathVariable Long id,
                                  @RequestBody @Valid TeacherRequestDto requestDto) {
        Teacher teacher = requestDtoMapper.toModel(requestDto);
        teacher.setId(id);
        Teacher savedTeacher = teacherService.save(teacher);
        return responseDtoMapper.toDto(savedTeacher);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete a teacher from DataBase.")
    public void delete(@PathVariable Long id) {
        teacherService.deleteById(id);
    }

    @PostMapping("/{id}/students/{studentId}")
    @ApiOperation("Recording a particular student with ID to a teacher with ID.")
    public TeacherResponseDto assignStudent(@PathVariable Long id,
                                            @PathVariable Long studentId) {
        Teacher teacher = teacherService.assignStudentToTeacherById(id,
                studentService.findById(studentId));
        return responseDtoMapper.toDto(teacher);
    }

    @DeleteMapping("/{id}/students/{studentId}")
    @ApiOperation("Unsubscribe a student from a particular teacher by their id.")
    public void deleteStudent(@PathVariable Long id,
                              @PathVariable Long studentId) {
        teacherService.removeStudentFromTeacherByIdAndStudentId(id,
                studentService.findById(studentId));
    }

    @GetMapping
    @ApiOperation("Get all teachers with pagination and sorting. "
            + "Template: *attribute*:*ORDER*. Example: id:DESC. By default: id:ASC.")
    public List<TeacherResponseDto> getAll(@RequestParam(defaultValue = "0") Integer page,
                                           @RequestParam(defaultValue = "10") Integer size,
                                           @RequestParam(defaultValue = "id") String sortBy) {
        Sort sort = SortUtil.sort(sortBy);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return teacherService.findAll(pageRequest)
                .stream()
                .map(responseDtoMapper::toDto)
                .toList();
    }

    @GetMapping("/students")
    @ApiOperation("Get all teachers by a particular student (first name and last name)."
            + "Pagination and sorting are included.")
    public List<TeacherResponseDto> getAllByStudent(@RequestParam String firstName,
                                                    @RequestParam String lastName,
                                                    @RequestParam(defaultValue = "0") Integer page,
                                                    @RequestParam(defaultValue = "10") Integer size,
                                                    @RequestParam(defaultValue = "id")
                                                    String sortBy) {
        Sort sort = SortUtil.sort(sortBy);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return teacherService.findAllByStudentName(firstName, lastName, pageRequest)
                .stream()
                .map(responseDtoMapper::toDto)
                .toList();
    }

    @GetMapping("/name")
    @ApiOperation("Get all teachers by their first and last names.")
    public List<TeacherResponseDto> getByName(@RequestParam String firstName,
                                              @RequestParam String lastName,
                                              @RequestParam(defaultValue = "0") Integer page,
                                              @RequestParam(defaultValue = "10") Integer size,
                                              @RequestParam(defaultValue = "id") String sortBy) {
        Sort sort = SortUtil.sort(sortBy);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return teacherService.findAllByName(firstName, lastName, pageRequest)
                .stream()
                .map(responseDtoMapper::toDto)
                .toList();
    }
}
