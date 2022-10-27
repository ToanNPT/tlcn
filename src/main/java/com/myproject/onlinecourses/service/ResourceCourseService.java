package com.myproject.onlinecourses.service;

import com.myproject.onlinecourses.dto.ResourceDTO;
import com.myproject.onlinecourses.dto.ResourcesCourseDTO;
import com.myproject.onlinecourses.dto.ResponseObject;

public interface ResourceCourseService {
    ResponseObject getAllByCourseId(String courseId);

    ResponseObject getResourceById(int id);

    ResponseObject updateResource(int id, ResourcesCourseDTO dto);

    ResponseObject addResource(String username, String courseId, ResourceDTO dto);
}
