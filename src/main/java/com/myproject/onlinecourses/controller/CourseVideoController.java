package com.myproject.onlinecourses.controller;

import com.myproject.onlinecourses.dto.CourseVideoDTO;
import com.myproject.onlinecourses.dto.ResponseObject;
import com.myproject.onlinecourses.service.CourseVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
public class CourseVideoController {

    @Autowired
    CourseVideoService courseVideoService;

    @GetMapping("course-video/video/{id}")
    public ResponseObject getById(@PathVariable("id") Integer id){
        return courseVideoService.getCourseVideoById(id);
    }

    @GetMapping("course-video")
    public ResponseObject getAll(){
        return courseVideoService.getAll();
    }

    @GetMapping("course-video/{courseId}")
    public ResponseObject getByCourseVideosId(@PathVariable("courseId") String id){
        return courseVideoService.getVideosByCourseId(id);
    }

    @GetMapping("course-video/chapter/{name}")
    public ResponseObject getCourseVideosByChapter(@PathVariable("name") String chapter){
        return courseVideoService.getVideosByChapter(chapter);
    }

    @DeleteMapping("course-video/{id}")
    public ResponseObject delete(@PathVariable("id") Integer id){
        return courseVideoService.deleteCourseVideo(id);
    }

    @PutMapping("course-video/edit-info/{id}")
    public ResponseObject updateInfor(@PathVariable("id") Integer id,
                                      @RequestBody CourseVideoDTO dto){
        return courseVideoService.updateInfoVideo(id, dto);
    }
}
