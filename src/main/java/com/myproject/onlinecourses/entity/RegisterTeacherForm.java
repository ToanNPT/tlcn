package com.myproject.onlinecourses.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterTeacherForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "teaching_topic")
    private String teachingTopic;

    @Column(name = "exp_describe")
    private String expDescribe;

    @Column(name = "current_job")
    private String currentJob;

    @Column(name = "address")
    private String address;

    @Column(name = "submit_time")
    private String submitTime;

    @Column(name = "status")
    private String status;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
    private Account account;
}
