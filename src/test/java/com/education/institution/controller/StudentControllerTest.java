package com.education.institution.controller;

import com.education.institution.model.Student;
import com.education.institution.model.Teacher;
import com.education.institution.model.dto.request.StudentRequestDto;
import com.education.institution.service.StudentService;
import com.education.institution.service.TeacherService;
import com.education.institution.util.SortUtil;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.util.Collections;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest {
    @MockBean
    private StudentService studentService;
    @MockBean
    private TeacherService teacherService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void shouldCreateStudent() {
        Student student = new Student();
        student.setFirstName("Alex");
        student.setLastName("Alexandrov");
        student.setAge(19);
        student.setEmail("test@test.com");
        student.setSpeciality("CS");
        student.setTeachers(Collections.emptyList());
        Mockito.when(studentService.save(student))
                .thenReturn(new Student(6L, "Alex", "Alexandrov",
                19, "test@test.com", "CS", Collections.emptyList()));

        StudentRequestDto requestDto = new StudentRequestDto();
        requestDto.setFirstName("Alex");
        requestDto.setLastName("Alexandrov");
        requestDto.setAge(19);
        requestDto.setEmail("test@test.com");
        requestDto.setSpeciality("CS");
        requestDto.setTeachers(Collections.emptyList());

        RestAssuredMockMvc
                .given()
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when()
                .post("/students")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(6))
                .body("firstName", Matchers.equalTo("Alex"))
                .body("lastName", Matchers.equalTo("Alexandrov"))
                .body("age", Matchers.equalTo(19))
                .body("email", Matchers.equalTo("test@test.com"))
                .body("speciality", Matchers.equalTo("CS"))
                .body("teacherIds", Matchers.equalTo(Collections.emptyList()));
    }

    @Test
    void shouldAssignTeacher() {
        Student student = new Student();
        student.setId(1L);
        student.setFirstName("Alex");
        student.setLastName("Alexandrov");
        student.setAge(19);
        student.setEmail("test@test.com");
        student.setSpeciality("CS");
        student.setTeachers(Collections.emptyList());

        Teacher teacher = new Teacher();
        teacher.setId(3L);
        teacher.setFirstName("Liam");
        teacher.setLastName("Moriarty");
        teacher.setAge(35);
        teacher.setEmail("math@edu");
        teacher.setField("Math");
        teacher.setStudents(Collections.emptyList());

        Student returnStudent = new Student();
        returnStudent.setId(1L);
        returnStudent.setFirstName("Alex");
        returnStudent.setLastName("Alexandrov");
        returnStudent.setAge(19);
        returnStudent.setEmail("test@test.com");
        returnStudent.setSpeciality("CS");
        returnStudent.setTeachers(List.of(teacher));

        Mockito.when(teacherService.findById(3L)).thenReturn(teacher);
        Mockito.when(studentService.assignTeacherToStudentById(1L, teacher))
                .thenReturn(returnStudent);
    }

    @Test
    void shouldReturnAllStudents() {
        Student student1 = new Student(1L, "Alex", "1", 19,
                "test@test.com", "CS", Collections.emptyList());
        Student student2 = new Student(7L, "Alex", "2", 19,
                "test@test.com", "CS", Collections.emptyList());
        Student student3 = new Student(8L, "Alex", "3", 19,
                "test@test.com", "CS", Collections.emptyList());
        Student student4 = new Student(11L, "Alex", "4", 19,
                "test@test.com", "CS", Collections.emptyList());
        Student student5 = new Student(15L, "Alex", "5", 19,
                "test@test.com", "CS", Collections.emptyList());

        List<Student> returnStudentList = List.of(student1, student2, student3, student4, student5);

        Sort sort = SortUtil.sort("id");
        PageRequest pageRequest = PageRequest.of(0, 10, sort);
        Mockito.when(studentService.findAll(pageRequest)).thenReturn(returnStudentList);

        RestAssuredMockMvc
                .when()
                .get("/students")
                .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(5))
                .body("[0].id", Matchers.equalTo(1))
                .body("[0].lastName", Matchers.equalTo("1"))
                .body("[1].id", Matchers.equalTo(7))
                .body("[1].lastName", Matchers.equalTo("2"))
                .body("[2].id", Matchers.equalTo(8))
                .body("[2].lastName", Matchers.equalTo("3"))
                .body("[3].id", Matchers.equalTo(11))
                .body("[3].lastName", Matchers.equalTo("4"))
                .body("[4].id", Matchers.equalTo(15))
                .body("[4].lastName", Matchers.equalTo("5"));

    }

    @Test
    void shouldReturnAllByTeacher() {
        Teacher teacher = new Teacher(3L, "Liam", "Moriarty", 35,
                "math@edu", "Math", Collections.emptyList());
        Student student = new Student(1L, "Alex", "1", 19,
                "test@test.com", "CS", List.of(teacher));

        List<Student> returnStudentList = List.of(student);

        Sort sort = SortUtil.sort("id");
        PageRequest pageRequest = PageRequest.of(0, 10, sort);
        Mockito.when(studentService
                .findAllByTeacherName(teacher.getFirstName(), teacher.getLastName(), pageRequest))
                .thenReturn(returnStudentList);

        RestAssuredMockMvc
                .given()
                .queryParam("firstName", teacher.getFirstName())
                .queryParam("lastName", teacher.getLastName())
                .when()
                .get("/students/teachers")
                .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(1))
                .body("[0].id", Matchers.equalTo(1))
                .body("[0].lastName", Matchers.equalTo("1"))
                .body("[0].teacherIds[0]", Matchers.equalTo(3));
    }

    @Test
    void shouldGetAllByName() {
        Student student = new Student(1L, "Alex", "Alexandrov", 19,
                "test@test.com", "CS", Collections.emptyList());
        Student student2 = new Student(2L, "Alex", "Alexandrov", 21,
                "test2@test.com", "SE", Collections.emptyList());

        List<Student> returnStudentList = List.of(student, student2);

        Sort sort = SortUtil.sort("id");
        PageRequest pageRequest = PageRequest.of(0, 10, sort);
        Mockito.when(studentService
                .findAllByName(student.getFirstName(), student.getLastName(), pageRequest))
                .thenReturn(returnStudentList);

        RestAssuredMockMvc
                .given()
                .queryParam("firstName", student.getFirstName())
                .queryParam("lastName", student.getLastName())
                .when()
                .get("/students/name")
                .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(2))
                .body("[0].id", Matchers.equalTo(1))
                .body("[0].firstName", Matchers.equalTo("Alex"))
                .body("[0].lastName", Matchers.equalTo("Alexandrov"))
                .body("[0].email", Matchers.equalTo("test@test.com"))
                .body("[0].speciality", Matchers.equalTo("CS"))
                .body("[1].id", Matchers.equalTo(2))
                .body("[1].firstName", Matchers.equalTo("Alex"))
                .body("[1].lastName", Matchers.equalTo("Alexandrov"))
                .body("[1].email", Matchers.equalTo("test2@test.com"))
                .body("[1].speciality", Matchers.equalTo("SE"));
    }
}
