package com.myproject.onlinecourses.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "coursesvideo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoursesVideo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne()
    @JoinColumn(name = "chapter_id")
    private Chapter chapter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false)
    private Course course;

    @Column(name = "link")
    private String link;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "next_videoId")
    private int nextVideoId;

    @Column(name = "is_head_video")
    private boolean isHeadVideo;
}