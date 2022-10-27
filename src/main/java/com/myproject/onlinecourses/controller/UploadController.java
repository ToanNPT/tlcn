package com.myproject.onlinecourses.controller;

import com.myproject.onlinecourses.aws.AwsS3Service;
import com.myproject.onlinecourses.dto.ResourceDTO;
import com.myproject.onlinecourses.dto.ResponseObject;
import com.myproject.onlinecourses.dto.UploadVideoDTO;
import com.myproject.onlinecourses.service.CourseVideoService;
import com.myproject.onlinecourses.service.ResourceCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/")
public class UploadController {
    @Autowired
    CourseVideoService service;

    @Autowired
    ResourceCourseService resoureService;

    @RequestMapping(path = "video/upload/{username}/{courseId}", method = RequestMethod.POST,
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseObject uploadVideo(@PathVariable("username") String username,
                                      @PathVariable("courseId") String courseId,
                                      @ModelAttribute UploadVideoDTO dto,
                                      HttpServletRequest request){
        String principal = request.getUserPrincipal().getName();
        if(!principal.equals(username)){
            return new ResponseObject("400", "200", "Dont have permission", null);
        }
        return service.addNewVideo(dto, principal, courseId);
    }

    @PostMapping(path = "resource/upload/{username}/{courseId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseObject uploadResource(@PathVariable("username") String username,
                                         @PathVariable("courseId") String courseId,
                                         @ModelAttribute ResourceDTO dto){
        return resoureService.addResource(username, courseId, dto);
    }

}
