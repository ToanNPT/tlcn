package com.myproject.onlinecourses.repository;

import com.myproject.onlinecourses.entity.CoursesVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.servlet.annotation.WebServlet;
import java.util.List;
import java.util.Optional;

public interface CourseVideoRepository extends JpaRepository<CoursesVideo, Integer> {

    @Query("select c from CoursesVideo as c where c.course.id = :courseId and c.isActive = true")
    List<CoursesVideo> findAllByCourse_Id(@Param("courseId") String courseId);

    @Query("select c from CoursesVideo as c where c.chapter = :chapter and c.isActive = true")
    List<CoursesVideo> findAllByChapter(@Param("chapter") String chapter);

    @Query("select distinct c.chapter from CoursesVideo as c where c.course.id = :courseId")
    List<String> getChaptersByCourseId(@Param("courseId") String courseId);

    @Query("select v " +
            "from CoursesVideo as v " +
            "where v.chapter.id = :chapterId and v.isActive = true " +
            "order by v.isHeadVideo desc")
    List<CoursesVideo> getVideosByChapterId(@Param("chapterId") int chapterId);

    @Query("select v " +
            "from CoursesVideo as v " +
            "where v.id = :id and v.isActive = true")
    Optional<CoursesVideo> findById(@Param("id") Integer id);

    @Query("select v " +
            "from CoursesVideo as v " +
            "where v.nextVideoId = :nextVideoId and v.chapter.id = :chapterId and v.isActive = true")
    Optional<CoursesVideo> findByNextChapterId(@Param("chapterId") Integer chapterId,
                                               @Param("nextVideoId") Integer nextVideoId);

    @Query("select count(v) " +
            "from CoursesVideo as v " +
            "where v.chapter.id = :chapterId and v.isActive = true")
    Integer countVideosInChapter(@Param("chapterId") Integer chapterId);


}
