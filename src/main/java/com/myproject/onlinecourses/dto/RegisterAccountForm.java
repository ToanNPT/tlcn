package com.myproject.onlinecourses.dto;

import com.myproject.onlinecourses.utils.RegexPattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterAccountForm {
    @NotEmpty
    @Length(max = 26, message = "length is exceeded 26 characters")
    private String username;

    @NotBlank(message = "password can not empty")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @NotEmpty(message = "fullname can not empty")
    private String fullname;

    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private Date birthdate;

    @NotEmpty(message = "gender can not empty")
    private String gender;

    @NotEmpty(message = "phone can not empty")
    @Pattern(regexp = RegexPattern.EMAIL_REGEX, message = "Email is invalid")
    private String email;

    @NotEmpty(message = "phone can not empty")
    @Pattern(regexp = RegexPattern.PHONE_REGEX, message = "Phone is invalid")
    private String phone;

    private MultipartFile avatar;
}
