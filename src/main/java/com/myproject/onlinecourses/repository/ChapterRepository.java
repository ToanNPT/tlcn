package com.myproject.onlinecourses.repository;

import com.myproject.onlinecourses.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChapterRepository extends JpaRepository<Chapter, Integer> {

    @Query("select c " +
            "from Chapter as c " +
            "where c.course.id = :courseId " +
            "order by c.headChapter desc ")
    List<Chapter> getChaptersByCourseId(String courseId);

    @Query("select c " +
            "from Chapter as c " +
            "where c.course.id = :courseId and c.nextChapterId = -1")
    Optional<Chapter> getEndChapterInCourse(String courseId);

    @Query("select c " +
            "from Chapter as c " +
            "where c.nextChapterId = :nextId and c.course.id = :courseId")
    Optional<Chapter> getChapterByNextId(String courseId, int nextId);
}
