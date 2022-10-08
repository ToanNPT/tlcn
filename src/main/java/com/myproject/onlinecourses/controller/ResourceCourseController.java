package com.myproject.onlinecourses.controller;

import com.myproject.onlinecourses.dto.ResourcesCourseDTO;
import com.myproject.onlinecourses.dto.ResponseObject;
import com.myproject.onlinecourses.service.ResourceCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
public class ResourceCourseController {
    @Autowired
    ResourceCourseService service;

    @GetMapping("resource/course/{courseId}")
    public ResponseObject getByCourseId(@PathVariable("courseId") String id){
        return service.getAllByCourseId(id);
    }

    @GetMapping("resource/{id}")
    public ResponseObject getByID(@PathVariable("id") int id){
        return service.getResourceById(id);
    }

    @PutMapping("resource/update/{id}")
    public ResponseObject updateResource(@PathVariable("id") int id, @RequestBody ResourcesCourseDTO dto){
        return service.updateResource(id, dto);
    }
}
