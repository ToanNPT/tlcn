package com.myproject.onlinecourses.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "userdetail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDetail {
    @Id
    @Column(name = "user_name")
    private String  username;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "birthdate")
    private Date birthdate;

    @Column(name = "gender")
    private String gender;

    @Column(name = "email")

    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "avatar")
    private String avatar;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_name")
    private Account account;
}
