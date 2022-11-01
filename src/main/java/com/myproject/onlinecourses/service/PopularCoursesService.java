package com.myproject.onlinecourses.service;

import com.myproject.onlinecourses.dto.ResponseObject;

public interface PopularCoursesService {
    ResponseObject getNewestCourse(int limit);

    ResponseObject getTopNumStudents(int limit);
}
