package com.myproject.onlinecourses.service;

import com.myproject.onlinecourses.dto.CourseDTO;
import com.myproject.onlinecourses.dto.ResponseObject;
import com.myproject.onlinecourses.dto.SearchCriteria;
import com.myproject.onlinecourses.dto.UploadCourse;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CoursesService {

    ResponseObject getById(String id);

    ResponseObject getAll(Optional<Integer> page);

    ResponseObject filterCourses(List<SearchCriteria> conditions);

    ResponseObject saveCourse(UploadCourse dto);

    ResponseObject delete(String courseId);

    ResponseObject update(String id, UploadCourse dto);

    ResponseObject checkPurchaseCourse(String username, String courseId);

    ResponseObject getListPurchasedCourse(String username);
}
