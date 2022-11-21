package com.education.institution.controller;

import com.education.institution.model.Student;
import com.education.institution.model.dto.request.StudentRequestDto;
import com.education.institution.model.dto.response.StudentResponseDto;
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
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final ResponseDtoMapper<StudentResponseDto, Student> responseDtoMapper;
    private final RequestDtoMapper<StudentRequestDto, Student> requestDtoMapper;

    public StudentController(StudentService studentService,
                             TeacherService teacherService,
                             ResponseDtoMapper<StudentResponseDto, Student> responseDtoMapper,
                             RequestDtoMapper<StudentRequestDto, Student> requestDtoMapper) {
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.responseDtoMapper = responseDtoMapper;
        this.requestDtoMapper = requestDtoMapper;
    }

    @PostMapping
    @ApiOperation("Create new student.")
    public StudentResponseDto create(@RequestBody @Valid StudentRequestDto requestDto) {
        Student student = studentService.save(requestDtoMapper.toModel(requestDto));
        return responseDtoMapper.toDto(student);
    }

    @PostMapping("/{id}")
    @ApiOperation("Changing an existing student.")
    public StudentResponseDto set(@PathVariable Long id,
                                  @RequestBody @Valid StudentRequestDto requestDto) {
        Student student = requestDtoMapper.toModel(requestDto);
        student.setId(id);
        Student savedStudent = studentService.save(student);
        return responseDtoMapper.toDto(savedStudent);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete a student from DataBase.")
    public void delete(@PathVariable Long id) {
        studentService.deleteById(id);
    }

    @PostMapping("/{id}/teachers/{teacherId}")
    @ApiOperation("Recording a particular teacher with ID to a student with ID.")
    public StudentResponseDto assignTeacher(@PathVariable Long id,
                                            @PathVariable Long teacherId) {
        Student student = studentService.assignTeacherToStudentById(id,
                teacherService.findById(teacherId));
        return responseDtoMapper.toDto(student);
    }

    @DeleteMapping("/{id}/teachers/{teacherId}")
    @ApiOperation("Unsubscribe a teacher from a particular student by their id.")
    public void deleteTeacher(@PathVariable Long id,
                              @PathVariable Long teacherId) {
        studentService.removeTeacherFromStudentByIdAndTeacherId(id,
                teacherService.findById(teacherId));
    }

    @GetMapping
    @ApiOperation("Get all teachers with pagination and sorting. "
            + "Template: *attribute*:*ORDER*. Example: id:DESC. By default: id:ASC.")
    public List<StudentResponseDto> getAll(@RequestParam(defaultValue = "0") Integer page,
                                           @RequestParam(defaultValue = "10") Integer size,
                                           @RequestParam(defaultValue = "id") String sortBy) {
        Sort sort = SortUtil.sort(sortBy);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return studentService.findAll(pageRequest)
                .stream()
                .map(responseDtoMapper::toDto)
                .toList();
    }

    @GetMapping("/teachers")
    @ApiOperation("Get all students by a particular teacher (first name and last name). "
            + "Pagination and sorting are included.")
    public List<StudentResponseDto> getAllByTeacher(@RequestParam String firstName,
                                                    @RequestParam String lastName,
                                                    @RequestParam(defaultValue = "0") Integer page,
                                                    @RequestParam(defaultValue = "10") Integer size,
                                                    @RequestParam(defaultValue = "id")
                                                    String sortBy) {
        Sort sort = SortUtil.sort(sortBy);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return studentService.findAllByTeacherName(firstName, lastName, pageRequest)
                .stream()
                .map(responseDtoMapper::toDto)
                .toList();
    }

    @GetMapping("/name")
    @ApiOperation("Get all students by first and last name. "
            + "Pagination and sorting are included.")
    public List<StudentResponseDto> getByName(@RequestParam String firstName,
                                              @RequestParam String lastName,
                                              @RequestParam(defaultValue = "0") Integer page,
                                              @RequestParam(defaultValue = "10") Integer size,
                                              @RequestParam(defaultValue = "id") String sortBy) {
        Sort sort = SortUtil.sort(sortBy);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return studentService.findAllByName(firstName, lastName, pageRequest)
                .stream()
                .map(responseDtoMapper::toDto)
                .toList();
    }
}
