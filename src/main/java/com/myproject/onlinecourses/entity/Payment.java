package com.myproject.onlinecourses.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "payment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @Id
    @GenericGenerator(name = "payment_id", strategy = "com.myproject.onlinecourses.generator.PaymentGenerator")
    @GeneratedValue(generator = "payment_id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "isActive")
    private boolean isActive;
}
