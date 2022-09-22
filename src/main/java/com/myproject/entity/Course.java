package com.myproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @Id
    @GenericGenerator(name = "course_id", strategy = "com.myproject.generator.CourseGenerator")
    @GeneratedValue(generator = "course_id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private Category category;

    @Column(name = "language")
    private String language;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_created", referencedColumnName = "user_name", nullable = false)
    private Account account;

    @Column(name = "price")
    private String price;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "num_students")
    private int numStudents;

    @Column(name = "is_public")
    private boolean isPublic;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    List<CoursesVideo> coursesVideoList;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    List<ResourcesCourse> resourcesCourseList;
}
