package com.education.institution.repository;

import com.education.institution.model.Student;
import com.education.institution.model.Teacher;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findAllByFirstNameAndLastName(String firstName, String lastName,
                                                Pageable pageable);

    List<Student> findAllByTeachersIn(List<Teacher> teachers, Pageable pageable);
}
