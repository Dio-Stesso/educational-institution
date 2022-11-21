package com.education.institution.model.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StudentResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String email;
    private String speciality;
    private List<Long> teacherIds;
}
