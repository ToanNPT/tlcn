package com.myproject.onlinecourses.repository;

import com.myproject.onlinecourses.entity.CoursesVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseVideoRepository extends JpaRepository<CoursesVideo, Integer> {

    @Query("select c from CoursesVideo as c where c.course.id = :courseId and c.isActive = true")
    List<CoursesVideo> findAllByCourse_Id(String courseId);

    @Query("select c from CoursesVideo as c where c.chapter = :chapter and c.isActive = true")
    List<CoursesVideo> findAllByChapter(String chapter);

    @Query("select distinct c.chapter from CoursesVideo as c where c.course.id = :courseId")
    List<String> getChaptersByCourseId(String courseId);

}
