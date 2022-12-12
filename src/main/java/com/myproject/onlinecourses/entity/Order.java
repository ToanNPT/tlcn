package com.myproject.onlinecourses.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @Column(length = 255)
//    @GenericGenerator(name = "order_id", strategy = "com.myproject.onlinecourses.generator.OrderGenerator")
//    @GeneratedValue(generator = "order_id")
    private String id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_name", referencedColumnName = "user_name", nullable = false)
    private Account account;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "payment_price")
    private double paymentPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_code", referencedColumnName = "code", nullable = true)
    private Coupon coupon;

    @Column(name = "qty")
    private int qty;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "is_active")
    private boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", referencedColumnName = "id", nullable = false)
    private Payment payment;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    List<OrderDetail> orderDetailList;
}
