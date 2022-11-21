package com.education.institution.service.impl;

import com.education.institution.model.Student;
import com.education.institution.model.Teacher;
import com.education.institution.repository.StudentRepository;
import com.education.institution.repository.TeacherRepository;
import com.education.institution.service.StudentService;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository repository;
    private final TeacherRepository teacherRepository;

    public StudentServiceImpl(StudentRepository repository,
                              TeacherRepository teacherRepository) {
        this.repository = repository;
        this.teacherRepository = teacherRepository;
    }

    @Override
    public Student save(Student student) {
        return repository.save(student);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Student findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Can't find a student by id."));
    }

    @Override
    public Student assignTeacherToStudentById(Long id, Teacher teacher) {
        Student student = findById(id);
        student.addTeacher(teacher);
        teacherRepository.save(teacher);
        return student;
    }

    @Override
    public void removeTeacherFromStudentByIdAndTeacherId(Long id, Teacher teacher) {
        Student student = findById(id);
        student.removeTeacher(teacher);
        repository.save(student);
    }

    @Override
    public List<Student> findAll(PageRequest pageRequest) {
        return repository.findAll(pageRequest).toList();
    }

    @Override
    public List<Student> findAllByName(String firstName, String lastName, PageRequest pageRequest) {
        return repository.findAllByFirstNameAndLastName(firstName, lastName, pageRequest);
    }

    @Override
    public List<Student> findAllByTeacherName(String firstName, String lastName,
                                              PageRequest pageRequest) {
        List<Teacher> teachers =
                teacherRepository.findAllByFirstNameAndLastName(firstName, lastName, pageRequest);
        return repository.findAllByTeachersIn(teachers, pageRequest);
    }
}
