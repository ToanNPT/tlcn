package com.myproject.onlinecourses.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "role")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GenericGenerator(name="role_id", strategy = "com.myproject.onlinecourses.generator.RoleGenerator")
    @GeneratedValue(generator = "role_id")
    private String id;

    @Column(name = "name")
    private String name;
}
