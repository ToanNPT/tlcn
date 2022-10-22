package com.myproject.onlinecourses.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UploadVideoDTO {
    private int id;
    private String chapter;
    private String title;
    private String description;
    private MultipartFile video;
}
