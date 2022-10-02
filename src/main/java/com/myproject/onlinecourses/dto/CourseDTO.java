package com.myproject.onlinecourses.dto;

import com.myproject.onlinecourses.entity.Account;
import com.myproject.onlinecourses.entity.Category;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
    private String id;
    private boolean isActive;
    private Category category;
    private String language;
    private String name;
    private String description;
    private String accountName;
    private String price;
    private Date createDate;
    private Date updateDate;
    private int numStudents;
    private boolean isPublic;


}
