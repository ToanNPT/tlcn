package com.myproject.onlinecourses.dto;

import com.myproject.onlinecourses.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseVideoDTO {
    private int id;
    private String chapter;
    private String courseId;
    private String link;
    private String title;
    private String description;
    private boolean isActive;
    private Date createDate;
    private Date updateDate;
}
