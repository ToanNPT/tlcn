package com.myproject.onlinecourses.dto;

import com.myproject.onlinecourses.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadCourse {
    private boolean isActive;
    private String category;
    private String language;
    private String name;
    private String description;
    private String accountName;
    private String price;
    private int numStudents;
    private boolean isPublic;
    private MultipartFile avatar;
}
