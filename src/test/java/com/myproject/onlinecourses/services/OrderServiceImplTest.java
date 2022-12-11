package com.myproject.onlinecourses.services;

import com.myproject.onlinecourses.service.OrderService;
import com.myproject.onlinecourses.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

//@RunWith(SpringRunner.class)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderServiceImplTest {

    @InjectMocks
    OrderServiceImpl orderService;

//    @BeforeEach
//     public void init(){
//        OrderService order = new OrderServiceImpl();
//        this.orderService = order;
//    }

    @Test
    public void handleActiveOrderTest(){
        String orderId = "30890040";
        orderService.handleActiveOrder(orderId);
    }
}