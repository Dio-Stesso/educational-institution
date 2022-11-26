package com.education.institution.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.education.institution.model.Student;
import com.education.institution.model.Teacher;
import com.education.institution.repository.TeacherRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TeacherServiceImplTest {
    @InjectMocks
    private TeacherServiceImpl service;

    @Mock
    private TeacherRepository repository;

    @Test
    void shouldAssignStudentToTeacherById() {
        Teacher teacher = new Teacher(1L, "Liam", "Moriarty", 32,
                "math@math.edu", "Math", new ArrayList<>());
        Student student = new Student(1L, "Alex", "Alexandrov", 19,
                "test@test.com", "CS", new ArrayList<>());

        Mockito.when(repository.findById(teacher.getId())).thenReturn(Optional.of(teacher));
        service.assignStudentToTeacherById(teacher.getId(), student);
        assertEquals(teacher.getStudents().size(), 1);
        assertEquals(teacher.getStudents().get(0), student);
    }

    @Test
    void shouldRemoveStudentFromTeacherByIdAndStudentId() {
        Teacher teacher = new Teacher(1L, "Liam", "Moriarty", 32,
                "math@math.edu", "Math", new ArrayList<>());
        Student student = new Student(1L, "Alex", "Alexandrov", 19,
                "test@test.com", "CS", new ArrayList<>(List.of(teacher)));
        teacher.setStudents(new ArrayList<>(List.of(student)));

        Mockito.when(repository.findById(teacher.getId())).thenReturn(Optional.of(teacher));
        service.removeStudentFromTeacherByIdAndStudentId(teacher.getId(), student);

        assertEquals(teacher.getStudents().size(), 0);
    }
}