package com.myproject.onlinecourses.controller;

import com.myproject.onlinecourses.dto.ChapterDTO;
import com.myproject.onlinecourses.dto.NewChapterDTO;
import com.myproject.onlinecourses.dto.ResponseObject;
import com.myproject.onlinecourses.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/")
public class ChapterController {

    @Autowired
    ChapterService chapterService;

    @GetMapping("courses/{courseId}/chapters")
    public ResponseObject getChaptersByCourse(@PathVariable("courseId") String courseId){
        return chapterService.getChaptersByCourse(courseId);
    }

    @GetMapping("courses/{courseId}/chapters/{id}")
    public ResponseObject getChapterById(@PathVariable("id") Integer id){
        return chapterService.getChapterById(id);
    }

    @PutMapping("courses/{courseId}/chapters/{chapterId}")
    public ResponseObject updateChapter(@RequestBody NewChapterDTO dto,
                                        @PathVariable("chapterId") Integer chapterId){
        return chapterService.updateChapterInfor(chapterId, dto);
    }

    @PostMapping("courses/{courseId}/chapters")
    public ResponseObject addNewChapter(@RequestBody NewChapterDTO dto){
        return chapterService.addNewChapter(dto);
    }

    @DeleteMapping("courses/{coursesId}/chapters/{chapterId}")
    public ResponseObject deleteChapter(@PathVariable("chapterId") Integer chapterId){
        return chapterService.deleteChapter(chapterId);
    }
}
