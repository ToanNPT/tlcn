package com.myproject.onlinecourses.dto;

import com.myproject.onlinecourses.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChapterDTO {
    private int id;
    private String courseId;
    private String chapterName;
    private int numVideos;
    private boolean headChapter;
    private int nextChapterId;
}
