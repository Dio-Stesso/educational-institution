package com.education.institution.service.mapper;

import com.education.institution.model.Student;
import com.education.institution.model.Teacher;
import com.education.institution.model.dto.request.StudentRequestDto;
import com.education.institution.model.dto.response.StudentResponseDto;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper implements ResponseDtoMapper<StudentResponseDto, Student>,
        RequestDtoMapper<StudentRequestDto, Student> {
    @Override
    public Student toModel(StudentRequestDto dto) {
        Student student = new Student();
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setAge(dto.getAge());
        student.setEmail(dto.getEmail());
        student.setSpeciality(dto.getSpeciality());
        student.setTeachers(dto.getTeachers());
        return student;
    }

    @Override
    public StudentResponseDto toDto(Student model) {
        StudentResponseDto responseDto = new StudentResponseDto();
        responseDto.setId(model.getId());
        responseDto.setFirstName(model.getFirstName());
        responseDto.setLastName(model.getLastName());
        responseDto.setAge(model.getAge());
        responseDto.setEmail(model.getEmail());
        responseDto.setSpeciality(model.getSpeciality());
        responseDto.setTeacherIds(model.getTeachers()
                .stream()
                .map(Teacher::getId)
                .toList());
        return responseDto;
    }
}
