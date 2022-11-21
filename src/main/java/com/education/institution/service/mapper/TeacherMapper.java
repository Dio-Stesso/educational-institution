package com.education.institution.service.mapper;

import com.education.institution.model.Student;
import com.education.institution.model.Teacher;
import com.education.institution.model.dto.request.TeacherRequestDto;
import com.education.institution.model.dto.response.TeacherResponseDto;
import org.springframework.stereotype.Component;

@Component
public class TeacherMapper implements ResponseDtoMapper<TeacherResponseDto, Teacher>,
        RequestDtoMapper<TeacherRequestDto, Teacher> {

    @Override
    public Teacher toModel(TeacherRequestDto dto) {
        Teacher teacher = new Teacher();
        teacher.setFirstName(dto.getFirstName());
        teacher.setLastName(dto.getLastName());
        teacher.setAge(dto.getAge());
        teacher.setEmail(dto.getEmail());
        teacher.setField(dto.getField());
        teacher.setStudents(dto.getStudents());
        return teacher;
    }

    @Override
    public TeacherResponseDto toDto(Teacher model) {
        TeacherResponseDto responseDto = new TeacherResponseDto();
        responseDto.setId(model.getId());
        responseDto.setFirstName(model.getFirstName());
        responseDto.setLastName(model.getLastName());
        responseDto.setAge(model.getAge());
        responseDto.setEmail(model.getEmail());
        responseDto.setField(model.getField());
        responseDto.setStudentIds(model.getStudents()
                .stream()
                .map(Student::getId)
                .toList());
        return responseDto;
    }
}
