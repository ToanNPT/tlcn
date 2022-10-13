package com.myproject.onlinecourses.controller;

import com.myproject.onlinecourses.dto.CourseDTO;
import com.myproject.onlinecourses.dto.ResponseObject;
import com.myproject.onlinecourses.dto.SearchCriteria;
import com.myproject.onlinecourses.entity.Course;
import com.myproject.onlinecourses.repository.CoursesRepository;
import com.myproject.onlinecourses.service.CoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/")
public class CoursesController {
    @Autowired
    CoursesService coursesService;

    @GetMapping("courses")
    public ResponseObject getAllCourses(@RequestParam Optional<Integer> page){
        return coursesService.getAll(page);
    }


    @GetMapping("course/{id}")
    public ResponseObject getById(@PathVariable("id") String id){
        return coursesService.getById(id);
    }

    @PostMapping("courses/search")
    public ResponseObject filterProduct(@RequestBody List<SearchCriteria> conditions){
        return coursesService.filterCourses(conditions);
    }

    @PostMapping("course/add")
    public ResponseObject save(@RequestBody CourseDTO course){
        return coursesService.saveCourse(course);
    }

    @DeleteMapping("course/delete/{courseId}")
    public ResponseObject delete(@PathVariable("courseId") String id){
        return coursesService.delete(id);

    }

    @PutMapping("course/update/{id}")
    public ResponseObject update(@PathVariable("id") String id, @RequestBody CourseDTO dto){
        return coursesService.update(id, dto);
    }
}
