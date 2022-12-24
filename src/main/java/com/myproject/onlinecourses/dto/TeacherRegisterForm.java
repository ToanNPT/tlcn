package com.myproject.onlinecourses.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherRegisterForm {
    private String phone;

    @NotNull(message = "email can not be null")
    private String email;

    @NotNull(message = "teaching topic can not be null")
    private String teachingTopic;

    @NotNull(message = "exp describe can not be null")
    private String expDescribe;

    @NotNull(message = "current job can not be null")
    private String currentJob;

    @NotNull(message = "address can not be null")
    private String address;
}
