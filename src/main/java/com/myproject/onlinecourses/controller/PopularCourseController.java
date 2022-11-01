package com.myproject.onlinecourses.controller;

import com.myproject.onlinecourses.dto.ResponseObject;
import com.myproject.onlinecourses.service.PopularCoursesService;
import com.myproject.onlinecourses.service.impl.PopularCoursesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class PopularCourseController {
    @Autowired
    PopularCoursesService popularCoursesService;

    @GetMapping("courses/newest")
    public ResponseObject getTopNewest(@RequestParam("limit") int limit){
        limit = limit <= 0 ? 8 : limit;
        return popularCoursesService.getNewestCourse(limit);
    }

    @GetMapping("courses/topNumStudents")
    public ResponseObject getTopNumsStudents(@RequestParam("limit") int limit){
        limit = limit <= 0 ? 8 : limit;
        return popularCoursesService.getTopNumStudents(limit);
    }
}
