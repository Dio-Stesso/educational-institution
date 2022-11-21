package com.education.institution.model.dto.request;

import com.education.institution.lib.ValidEmail;
import com.education.institution.model.Teacher;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StudentRequestDto {
    @Size(min = 3)
    private String firstName;
    private String lastName;
    @Min(19)
    private Integer age;
    @ValidEmail
    private String email;
    private String speciality;
    private List<Teacher> teachers;
}
