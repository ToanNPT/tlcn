package com.myproject.onlinecourses.dto;

import com.myproject.onlinecourses.utils.RegexPattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetailDTO {
    @NotEmpty
    @Length(max = 26, message = "length is exceeded 26 characters")
    private String username;

    @NotEmpty(message = "role can not empty")
    private String role;

    @NotBlank(message = "password can not empty")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

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
