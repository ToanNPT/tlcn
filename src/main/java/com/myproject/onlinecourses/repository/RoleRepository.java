package com.myproject.onlinecourses.repository;

import com.myproject.onlinecourses.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}
