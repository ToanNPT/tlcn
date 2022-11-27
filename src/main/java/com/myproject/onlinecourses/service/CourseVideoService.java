package com.myproject.onlinecourses.service;

import com.myproject.onlinecourses.dto.CourseVideoDTO;
import com.myproject.onlinecourses.dto.ResponseObject;
import com.myproject.onlinecourses.dto.UpdateInforVideo;
import com.myproject.onlinecourses.dto.UploadVideoDTO;

public interface CourseVideoService {
    ResponseObject getVideosByCourseId(String courseId);

    ResponseObject getAll();

    ResponseObject getCourseVideoById(Integer id);

    ResponseObject getVideosByChapter(Integer chapterId);

    ResponseObject addNewVideo(UploadVideoDTO dto, String username, String courseId, Integer chapterId);

    ResponseObject updateInfoVideo(int videoId, UpdateInforVideo form);

    ResponseObject deleteCourseVideo(int id);

}
