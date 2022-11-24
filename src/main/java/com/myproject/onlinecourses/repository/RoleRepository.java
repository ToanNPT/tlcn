package com.myproject.onlinecourses.repository;

import com.myproject.onlinecourses.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {

    @Query("select r " +
            "from Role as r " +
            "where r.name = :name")
    Optional<Role> findRoleByName(String name);
}
