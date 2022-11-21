package com.education.institution.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "teachers")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private Integer age;
    @Column(unique = true, nullable = false)
    private String email;
    private String field;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<Student> students;

    public void addStudent(Student student) {
        students.add(student);
        student.getTeachers().add(this);
    }

    public void removeStudent(Student student) {
        students.remove(student);
        student.getTeachers().remove(this);
    }
}

