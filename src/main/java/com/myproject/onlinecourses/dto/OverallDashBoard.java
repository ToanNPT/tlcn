package com.myproject.onlinecourses.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OverallDashBoard {
    private int studentsNum;
    private int teachersNum;
    private int coursesNum;
    private int requestToTeacher;
}
