package com.myproject.onlinecourses.converter;

import com.myproject.onlinecourses.dto.DetailOrder;
import com.myproject.onlinecourses.dto.OrderDTO;
import com.myproject.onlinecourses.dto.OrderDetailDTO;
import com.myproject.onlinecourses.entity.Order;
import com.myproject.onlinecourses.entity.OrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface OrderConverter {
    @Mappings({
            @Mapping(target = "username", source = "order.account.username"),
            @Mapping(target = "couponCode", source = "order.coupon.code"),
            @Mapping(target = "paymentName", source = "order.payment.name"),
            @Mapping(target = "orderDetailList", ignore = true)
    })
    OrderDTO orderToOrderDTO(Order order);

    @Mappings({
            @Mapping(target = "username", source = "order.account.username"),
            @Mapping(target = "couponCode", source = "order.coupon.code"),
            @Mapping(target = "paymentName", source = "order.payment.name"),
            @Mapping(target = "orderDetailList", ignore = true)
    })
    DetailOrder orderToDetailOrder(Order order);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "account", ignore = true),
            @Mapping(target = "payment", ignore = true),
            @Mapping(target = "coupon", ignore = true),
            @Mapping(target = "active", ignore = true)
    })
    Order orderDtoToOrder(OrderDTO dto);

    @Mappings({

    })
    OrderDetailDTO orderDetailToOrderDetailDTO(OrderDetail detail);
}
