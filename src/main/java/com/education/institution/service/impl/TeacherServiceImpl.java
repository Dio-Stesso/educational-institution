package com.education.institution.service.impl;

import com.education.institution.model.Student;
import com.education.institution.model.Teacher;
import com.education.institution.repository.StudentRepository;
import com.education.institution.repository.TeacherRepository;
import com.education.institution.service.TeacherService;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository repository;
    private final StudentRepository studentRepository;

    public TeacherServiceImpl(TeacherRepository repository, StudentRepository studentRepository) {
        this.repository = repository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Teacher save(Teacher model) {
        return repository.save(model);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Teacher findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Can't find a teacher by id."));
    }

    @Override
    public List<Teacher> findAll(PageRequest pageRequest) {
        return repository.findAll(pageRequest).toList();
    }

    @Override
    public List<Teacher> findAllByName(String firstName, String lastName, PageRequest pageRequest) {
        return repository.findAllByFirstNameAndLastName(firstName, lastName, pageRequest);
    }

    @Override
    public Teacher assignStudentToTeacherById(Long id, Student student) {
        Teacher teacher = findById(id);
        teacher.addStudent(student);
        return repository.save(teacher);
    }

    @Override
    public void removeStudentFromTeacherByIdAndStudentId(Long id, Student student) {
        Teacher teacher = findById(id);
        teacher.removeStudent(student);
        repository.save(teacher);
    }

    @Override
    public List<Teacher> findAllByStudentName(String firstName, String lastName,
                                              PageRequest pageRequest) {
        List<Student> students =
                studentRepository.findAllByFirstNameAndLastName(firstName, lastName, pageRequest);
        return repository.findAllByStudentsIn(students, pageRequest);
    }
}
