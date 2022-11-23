package com.myproject.onlinecourses.controller;

import com.myproject.onlinecourses.dto.CourseDTO;
import com.myproject.onlinecourses.dto.ResponseObject;
import com.myproject.onlinecourses.dto.SearchCriteria;
import com.myproject.onlinecourses.dto.UploadCourse;
import com.myproject.onlinecourses.entity.Course;
import com.myproject.onlinecourses.repository.CoursesRepository;
import com.myproject.onlinecourses.service.CoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.integration.IntegrationGraphEndpoint;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
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

//    @PostMapping(value = "course/add")
//    public ResponseObject save(@ModelAttribute CourseDTO course){
//        return coursesService.saveCourse(course);
//    }

    @PostMapping(value = "course/add" , consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseObject saveCourse(@ModelAttribute UploadCourse dto){
        return coursesService.saveCourse(dto);
    }

    @DeleteMapping("course/delete/{courseId}")
    public ResponseObject delete(@PathVariable("courseId") String id){
        return coursesService.delete(id);

    }

    @PutMapping(value = "course/update/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseObject update(@PathVariable("id") String id,
                                 @ModelAttribute UploadCourse dto){
        return coursesService.update(id, dto);
    }

    @GetMapping("isPurchaseCourse/{username}/{courseId}")
    public ResponseObject checkPurchasedCourse(@PathVariable("username") String username,
                                               @PathVariable("courseId") String courseId){
        return coursesService.checkPurchaseCourse(username, courseId);
    }

    @GetMapping("listPurchasedCourses/{username}")
    public ResponseObject getListPurchasedCourses(@PathVariable("username") String username,
                                                  @RequestParam("page") Optional<Integer> page,
                                                  @RequestParam("limit") Optional<Integer> limit){
        return coursesService.getListPurchasedCourse(username, page, limit);
    }

}
