package com.myproject.onlinecourses.service;

import com.myproject.onlinecourses.dto.CourseVideoDTO;
import com.myproject.onlinecourses.dto.ResponseObject;
import com.myproject.onlinecourses.dto.UploadVideoDTO;

public interface CourseVideoService {
    ResponseObject getVideosByCourseId(String courseId);

    ResponseObject getVideosByChapter(String chapter);

    ResponseObject addNewVideo(UploadVideoDTO dto, String username, String courseId);

    ResponseObject updateInfoVideo(int id, CourseVideoDTO dto);

    ResponseObject deleteCourseVideo(int id);
}
