package com.myproject.onlinecourses.dto;

import com.myproject.onlinecourses.utils.RegexPattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateInforForm {

    @NotEmpty(message = "fullname can not empty")
    private String fullname;

    private Date birthdate;

    @NotEmpty(message = "gender can not empty")
    private String gender;

    @NotEmpty(message = "phone can not empty")
    @Pattern(regexp = RegexPattern.EMAIL_REGEX, message = "Email is invalid")
    private String email;

    @NotEmpty(message = "phone can not empty")
    @Pattern(regexp = RegexPattern.PHONE_REGEX, message = "Phone is invalid")
    private String phone;
}
