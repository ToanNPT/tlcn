package com.myproject.onlinecourses.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "userdetail")
@Data
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

    @OneToOne()
    @JoinColumn(name = "user_name")
    private Account account;
}
