package com.myproject.onlinecourses.repository;

import com.myproject.onlinecourses.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    List<Review> findByAccount_Username(String username);

    Page<Review> findByCourse_Id(String id, Pageable pageable);
}
