package com.myproject.onlinecourses.repository;

import com.myproject.onlinecourses.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer > {

    @Query("select t " +
            "from Token as t " +
            "where t.account.username = :username and :date between t.iat and t.exp")
    List<Token> getValidTokenByUsername(@Param("username") String username,
                                        @Param("date") Date date);

    @Query("select t " +
            "from Token  as t " +
            "where t.token = :token")
    Optional<Token> getTokenByTokenValue(@Param("token") String token);
}
