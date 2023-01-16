package com.myproject.onlinecourses.repository;

import com.myproject.onlinecourses.entity.RegisterTeacherForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RegisterTeacherRepository extends JpaRepository<RegisterTeacherForm, Integer> {
    @Query("select r " +
            "from RegisterTeacherForm as r " +
            "where r.status = :status " +
            "order by r.submitTime")
    Page<RegisterTeacherForm> findRequestsByStatus(@Param("status") String status,
                                                   Pageable pageable);
}
