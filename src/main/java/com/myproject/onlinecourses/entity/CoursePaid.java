package com.myproject.onlinecourses.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "courses_paid")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoursePaid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "username")
    private Account account;

    @ManyToOne()
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "buy_date")
    private Date buyDate;
}
