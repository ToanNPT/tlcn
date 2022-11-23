package com.myproject.onlinecourses.service;

import com.myproject.onlinecourses.dto.NewChapterDTO;
import com.myproject.onlinecourses.dto.ResponseObject;

public interface ChapterService {
    ResponseObject getChapterById(Integer id);

    ResponseObject getChaptersByCourse(String courseId);

    ResponseObject addNewChapter(NewChapterDTO dto);

    ResponseObject updateChapterInfor(Integer chapterId, NewChapterDTO dto);

    ResponseObject deleteChapter(Integer chapterId);
}
