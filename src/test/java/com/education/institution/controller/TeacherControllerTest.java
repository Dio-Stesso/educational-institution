package com.education.institution.controller;

import com.education.institution.model.Student;
import com.education.institution.model.Teacher;
import com.education.institution.model.dto.request.TeacherRequestDto;
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
class TeacherControllerTest {
    @MockBean
    private TeacherService teacherService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void shouldCreateTeacher() {
        Teacher teacher = new Teacher();
        teacher.setFirstName("Liam");
        teacher.setLastName("Moriarty");
        teacher.setAge(35);
        teacher.setEmail("test@edu.com");
        teacher.setField("Math");
        teacher.setStudents(Collections.emptyList());
        Mockito.when(teacherService.save(teacher))
                .thenReturn(new Teacher(6L, "Liam", "Moriarty",
                        35, "test@edu.com", "Math", Collections.emptyList()));

        TeacherRequestDto requestDto = new TeacherRequestDto();
        requestDto.setFirstName("Liam");
        requestDto.setLastName("Moriarty");
        requestDto.setAge(35);
        requestDto.setEmail("test@edu.com");
        requestDto.setField("Math");
        requestDto.setStudents(Collections.emptyList());

        RestAssuredMockMvc
                .given()
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when()
                .post("/teachers")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(6))
                .body("firstName", Matchers.equalTo("Liam"))
                .body("lastName", Matchers.equalTo("Moriarty"))
                .body("age", Matchers.equalTo(35))
                .body("email", Matchers.equalTo("test@edu.com"))
                .body("field", Matchers.equalTo("Math"))
                .body("studentIds", Matchers.equalTo(Collections.emptyList()));
    }

    @Test
    void shouldAssignStudent() {
    }

    @Test
    void shouldGetAll() {
        Teacher teacher = new Teacher(1L, "Liam", "1", 35,
                "test@test.com", "Math", Collections.emptyList());
        Teacher teacher1 = new Teacher(3L, "Liam", "2", 35,
                "test@test.com", "Math", Collections.emptyList());
        Teacher teacher2 = new Teacher(4L, "Liam", "3", 35,
                "test@test.com", "Math", Collections.emptyList());
        Teacher teacher3 = new Teacher(7L, "Liam", "4", 35,
                "test@test.com", "Math", Collections.emptyList());
        Teacher teacher4 = new Teacher(8L, "Liam", "5", 35,
                "test@test.com", "Math", Collections.emptyList());

        List<Teacher> returnTeacherList = List.of(teacher, teacher1, teacher2, teacher3, teacher4);

        Sort sort = SortUtil.sort("id");
        PageRequest pageRequest = PageRequest.of(0, 10, sort);
        Mockito.when(teacherService.findAll(pageRequest)).thenReturn(returnTeacherList);

        RestAssuredMockMvc
                .when()
                .get("/teachers")
                .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(5))
                .body("[0].id", Matchers.equalTo(1))
                .body("[0].lastName", Matchers.equalTo("1"))
                .body("[1].id", Matchers.equalTo(3))
                .body("[1].lastName", Matchers.equalTo("2"))
                .body("[2].id", Matchers.equalTo(4))
                .body("[2].lastName", Matchers.equalTo("3"))
                .body("[3].id", Matchers.equalTo(7))
                .body("[3].lastName", Matchers.equalTo("4"))
                .body("[4].id", Matchers.equalTo(8))
                .body("[4].lastName", Matchers.equalTo("5"));
    }

    @Test
    void shouldGetAllByStudent() {
        Student student = new Student(1L, "Alex", "1", 19,
                "test@test.com", "CS", Collections.emptyList());
        Teacher teacher = new Teacher(3L, "Liam", "Moriarty", 35,
                "math@edu", "Math", List.of(student));

        List<Teacher> returnTeacherList = List.of(teacher);

        Sort sort = SortUtil.sort("id");
        PageRequest pageRequest = PageRequest.of(0, 10, sort);
        Mockito.when(teacherService.findAllByStudentName(student.getFirstName(),
                        student.getLastName(), pageRequest))
                .thenReturn(returnTeacherList);

        RestAssuredMockMvc
                .given()
                .queryParam("firstName", student.getFirstName())
                .queryParam("lastName", student.getLastName())
                .when()
                .get("/teachers/students")
                .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(1))
                .body("[0].id", Matchers.equalTo(3))
                .body("[0].lastName", Matchers.equalTo("Moriarty"))
                .body("[0].studentIds[0]", Matchers.equalTo(1));
    }

    @Test
    void shouldGetByName() {
        Teacher teacher = new Teacher(1L, "Liam", "Moriarty", 35,
                "test@test.com", "Math", Collections.emptyList());

        List<Teacher> returnTeacherList = List.of(teacher);

        Sort sort = SortUtil.sort("id");
        PageRequest pageRequest = PageRequest.of(0, 10, sort);
        Mockito.when(teacherService
                        .findAllByName(teacher.getFirstName(), teacher.getLastName(), pageRequest))
                .thenReturn(returnTeacherList);

        RestAssuredMockMvc
                .given()
                .queryParam("firstName", teacher.getFirstName())
                .queryParam("lastName", teacher.getLastName())
                .when()
                .get("/teachers/name")
                .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(1))
                .body("[0].id", Matchers.equalTo(1))
                .body("[0].firstName", Matchers.equalTo("Liam"))
                .body("[0].lastName", Matchers.equalTo("Moriarty"))
                .body("[0].age", Matchers.equalTo(35))
                .body("[0].email", Matchers.equalTo("test@test.com"))
                .body("[0].field", Matchers.equalTo("Math"))
                .body("[0].studentIds.size()", Matchers.equalTo(0));
    }
}