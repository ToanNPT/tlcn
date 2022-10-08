package com.myproject.onlinecourses.dto;

import com.myproject.onlinecourses.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResourcesCourseDTO {
    private int id;
    private String title;
    private String link;
    private String courseId;
}
