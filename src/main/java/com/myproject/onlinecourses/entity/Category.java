package com.myproject.onlinecourses.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "category")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GenericGenerator(name ="category_id", strategy = "com.myproject.onlinecourses.generator.CategoryGenerator")
    @GeneratedValue(generator = "category_id")
    private String id;

    @Column(name="name")
    private String name;

    @Column(name = "isActive")
    private boolean isActive;
}
