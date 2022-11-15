package com.myproject.onlinecourses.repository;

import com.myproject.onlinecourses.entity.CoursePaid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CoursePaidRepository extends JpaRepository<CoursePaid, Integer> {
    @Query("select c " +
            "from CoursePaid as c " +
            "where c.account.username = :username")
    List<CoursePaid> getCoursesPaidByUsername(String username);

    @Query("select c " +
            "from CoursePaid as c " +
            "where c.course.id = :courseId and c.account.username = :username")
    Optional<CoursePaid> isPaid(String courseId, String username);
}
