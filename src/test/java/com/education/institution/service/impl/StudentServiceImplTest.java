package com.education.institution.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.education.institution.model.Student;
import com.education.institution.model.Teacher;
import com.education.institution.repository.StudentRepository;
import com.education.institution.repository.TeacherRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {
    @InjectMocks
    private StudentServiceImpl service;

    @Mock
    private StudentRepository repository;
    @Mock
    private TeacherRepository teacherRepository;

    @Test
    void shouldAssignTeacherToStudentById() {
        Student student = new Student(1L, "Alex", "Alexandrov", 19,
                "test@test.com", "CS", new ArrayList<>());
        Teacher teacher = new Teacher(1L, "Liam", "Moriarty", 32,
                "math@math.edu", "Math", new ArrayList<>());

        Mockito.when(repository.findById(student.getId())).thenReturn(Optional.of(student));
        Mockito.when(teacherRepository.save(teacher)).thenReturn(teacher);
        Student resultStudent = service.assignTeacherToStudentById(student.getId(), teacher);
        assertEquals(resultStudent.getTeachers().size(), 1);
        assertEquals(resultStudent.getTeachers().get(0), teacher);
    }

    @Test
    void shouldRemoveTeacherFromStudentByIdAndTeacherId() {
        Student student = new Student(1L, "Alex", "Alexandrov", 19,
                "test@test.com", "CS", new ArrayList<>());
        Teacher teacher = new Teacher(1L, "Liam", "Moriarty", 32,
                "math@math.edu", "Math", new ArrayList<>(List.of(student)));
        student.setTeachers(new ArrayList<>(List.of(teacher)));

        Mockito.when(repository.findById(student.getId())).thenReturn(Optional.of(student));
        service.removeTeacherFromStudentByIdAndTeacherId(student.getId(), teacher);

        assertEquals(student.getTeachers().size(), 0);
    }
}