package com.myproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GenericGenerator(name="role_id", strategy = "com.myproject.generator.RoleGenerator")
    @GeneratedValue(generator = "role_id")
    private String id;

    @Column(name = "name")
    private String name;
}
