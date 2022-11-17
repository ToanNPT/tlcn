package com.myproject.onlinecourses.repository;

import com.myproject.onlinecourses.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, String> {

    Optional<Category> findFirstByName(String name);

    @Query("select c " +
            "from Category c " +
            "where c.isActive = :isActive " +
            "order by c.name asc ")
    List<Category> findAllByActive(boolean isActive);

    @Query("select c from Category c where c.id = :id and c.isActive = :isActive")
    Optional<Category> findByIdAndActive(String id, boolean isActive);

    @Query("select c " +
            "from Category c " +
            "where c.isActive = :isActive " +
            "order by c.name asc")
    Page<Category> findAllByActive(Pageable pageable, boolean isActive);
}
