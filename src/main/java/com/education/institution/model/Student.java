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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "students")
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private Integer age;
    //@Column(unique = true, nullable = false)
    private String email;
    private String speciality;
    @ManyToMany(mappedBy = "students", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<Teacher> teachers;

    public void addTeacher(Teacher teacher) {
        teachers.add(teacher);
        teacher.getStudents().add(this);
    }

    public void removeTeacher(Teacher teacher) {
        teachers.remove(teacher);
        teacher.getStudents().remove(this);
    }
}
