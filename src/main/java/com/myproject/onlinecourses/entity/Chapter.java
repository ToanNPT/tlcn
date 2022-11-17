package com.myproject.onlinecourses.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.util.List;

@Entity
@Table(name = "chapters")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Chapter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne()
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "chapter_name")
    private String chapterName;

    @Column(name = "num_videos")
    private int numVideos;

    @Column(name = "head_chapter")
    private boolean headChapter;

    @Column(name = "next_chapter_id")
    private int nextChapterId;


}
