package com.education.institution.service;

import com.education.institution.model.Student;
import com.education.institution.model.Teacher;
import java.util.List;
import org.springframework.data.domain.PageRequest;

public interface StudentService extends AbstractBasicService<Student> {

    Student assignTeacherToStudentById(Long id, Teacher teacher);

    void removeTeacherFromStudentByIdAndTeacherId(Long studentId, Teacher teacherId);

    List<Student> findAllByTeacherName(String firstName, String lastName, PageRequest pageRequest);
}
