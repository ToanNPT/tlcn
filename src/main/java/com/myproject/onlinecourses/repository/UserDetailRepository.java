package com.myproject.onlinecourses.repository;

import com.myproject.onlinecourses.entity.UserDetail;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailRepository extends JpaRepository<UserDetail, String> {
    public Optional<UserDetail> findFirstByPhoneOrEmailOrUsername(String phone, String email, String username);
}
