package com.myproject.onlinecourses.service;

import com.myproject.onlinecourses.dto.CourseVideoDTO;
import com.myproject.onlinecourses.dto.ResponseObject;

public interface CourseVideoService {
    ResponseObject getVideosByCourseId(String courseId);

    ResponseObject getVideosByChapter(String chapter);

    ResponseObject addNewVideo(CourseVideoDTO dto);

    ResponseObject updateInfoVideo(int id, CourseVideoDTO dto);

    ResponseObject deleteCourseVideo(int id);
}
