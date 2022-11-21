package com.education.institution.repository;

import com.education.institution.model.Student;
import com.education.institution.model.Teacher;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    List<Teacher> findAllByFirstNameAndLastName(String firstName, String lastName,
                                                Pageable pageable);

    List<Teacher> findAllByStudentsIn(List<Student> students, Pageable pageable);
}
