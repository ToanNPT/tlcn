package com.myproject.onlinecourses.repository;

import com.myproject.onlinecourses.entity.UserDetail;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserDetailRepository extends JpaRepository<UserDetail, String> {
    public Optional<UserDetail> findFirstByPhoneOrEmailOrUsername(@Param("phone") String phone,
                                                                  @Param("email") String email,
                                                                  @Param("username") String username);
}
