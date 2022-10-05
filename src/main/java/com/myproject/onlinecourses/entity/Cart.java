package com.myproject.onlinecourses.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cart")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    @Id
    @Column(name = "user_name")
    private String username;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_name")
    private Account account;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "payment_price")
    private double paymentPrice;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    List<CartDetail> cartDetailList;
}
