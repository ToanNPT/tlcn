package com.myproject.onlinecourses.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPass {
    @NotBlank(message = "password can not empty")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String newPwd;

    @NotBlank(message = "password can not empty")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String confirmPwd;
}
