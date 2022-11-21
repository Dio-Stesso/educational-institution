package com.education.institution.service;

import com.education.institution.model.Student;
import com.education.institution.model.Teacher;
import java.util.List;
import org.springframework.data.domain.PageRequest;

public interface TeacherService extends AbstractBasicService<Teacher> {
    Teacher assignStudentToTeacherById(Long id, Student student);

    void removeStudentFromTeacherByIdAndStudentId(Long id, Student student);

    List<Teacher> findAllByStudentName(String firstName, String lastName, PageRequest pageRequest);
}
