package com.myproject.onlinecourses.service.impl;

import com.myproject.onlinecourses.converter.CourseConverter;
import com.myproject.onlinecourses.converter.OrderConverter;
import com.myproject.onlinecourses.dto.*;
import com.myproject.onlinecourses.entity.*;
import com.myproject.onlinecourses.exception.NotFoundException;
import com.myproject.onlinecourses.repository.*;
import com.myproject.onlinecourses.service.CartService;
import com.myproject.onlinecourses.service.CouponService;
import com.myproject.onlinecourses.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.myproject.onlinecourses.entity.Coupon_.code;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepo;

    @Autowired
    OrderConverter converter;

    @Autowired
    CoursesRepository coursesRepo;

    @Autowired
    PaymentRepository paymentRepo;

    @Autowired
    CouponRepository couponRepo;

    @Autowired
    CouponService couponService;

    @Autowired
    AccountRepository accountRepo;

    @Autowired
    CoursePaidRepository coursePaidRepo;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartService cartService;

    @Autowired
    CourseConverter courseConverter;

    @Override
    public OrderDTO addOrder(RequestOrder dto){
        return null;
    }

    @Override
    public Order addUnActiveOrder(String paymentId, RequestOrder dto){
        double total = 0;
        for(String id : dto.getOrderDetailList()){
            Double price = coursesRepo.getPriceInCourse(id);
            total += price;
        }
        double paymentPrice = total;

        Optional<Account> account = accountRepo.findById(dto.getUsername());
        if(!account.isPresent())
            throw new NotFoundException("Can not found account");

        Order order = new Order();
        order.setAccount(account.get());
        if(dto.getCouponCode() == null || dto.getCouponCode() == "")
            order.setCoupon(null);
        else{
            Optional<Coupon> coupon = couponRepo.findByCode(dto.getCouponCode());
            order.setCoupon(coupon.orElse(null));
            if(coupon.get().getType().equals("%"))
                paymentPrice = paymentPrice - paymentPrice*(coupon.get().getValue()/100);
            else
                paymentPrice = paymentPrice - coupon.get().getValue();

        }
        order.setPaymentPrice(paymentPrice);
        order.setTotalPrice(total);
        order.setActive(false);
        order.setCreateDate(new Date());
        Optional<Payment> payment = paymentRepo.findById(dto.getPaymentId());
        order.setPayment(payment.get());
        order.setQty(dto.getOrderDetailList().size());

        List<OrderDetail> orderDetailList = dto.getOrderDetailList().stream()
                .map(p -> {
                    OrderDetail detail = new OrderDetail();
                    Optional<Course> course = coursesRepo.findById(p.trim());
                    detail.setCourse(course.get());
                    detail.setPrice(course.get().getPrice());
                    detail.setAccount(account.get());
                    detail.setOrder(order);
                    return detail;
                })
                .collect(Collectors.toList());

        order.setId(paymentId);
        order.setOrderDetailList(orderDetailList);
        return orderRepo.save(order);
    }

    @Override
    public boolean checkRequestOrder(RequestOrder dto){

        if(dto.getCouponCode() != null && !dto.getCouponCode().equals("")){
            Optional<Coupon> coupon = couponRepo.findById(dto.getCouponCode());
            if(!coupon.isPresent())
                throw new NotFoundException("Not found coupon " +  dto.getCouponCode());
            Date now = new Date();
            if(now.before(coupon.get().getStartDate()) || now.after(coupon.get().getExpiredDate())){
                throw new RuntimeException("Coupon is not valid");
            }
            if(coupon.get().getNumberOfRemain() == 0)
                throw new RuntimeException("Coupon is out of use");
        }


        Optional<Payment> payment = paymentRepo.findById(dto.getPaymentId());
        if(!payment.isPresent())
            throw new NotFoundException("Payment is not valid");

        List<String> paidList = coursePaidRepo.getIdsCoursePaidByUsername(dto.getUsername());
        List<String> duplicated = dto.getOrderDetailList().stream()
                .filter(paidList::contains)
                .collect(Collectors.toList());

        if(duplicated.size() != 0){
            StringBuilder messError = new StringBuilder();
            messError.append("Course with id ");
            duplicated.forEach(p -> messError.append(p).append(", "));
            if(duplicated.size()%2 == 0)
                messError.append("were ");
            else
                messError.append("was ");
            messError.append("bought before");
            throw new RuntimeException(messError.toString());
        }
        return true;
    }

    @Override
    public void activeOrder(String orderId){
        Optional<Order> order = orderRepo.findById(orderId);
        if(!order.isPresent())
            throw new NotFoundException("Can not found order");
        order.get().setActive(true);

        Account account = order.get().getAccount();
        List<CoursePaid> coursePaidList = new ArrayList<>();
        for(OrderDetail item: order.get().getOrderDetailList()){
            CoursePaid coursePaid = new CoursePaid();
            Course course = item.getCourse();
            coursePaid.setCourse(course);
            coursePaid.setAccount(account);
            coursePaid.setBuyDate(order.get().getCreateDate());
            coursePaidList.add(coursePaid);

            course.setNumStudents(course.getNumStudents() + 1);
            coursesRepo.save(course);
        }
        coursePaidRepo.saveAll(coursePaidList);
        orderRepo.save(order.get());
    }

    @Override
    public void deleteUnActiveorder(String orderId){
        Optional<Order> order = orderRepo.findById(orderId);
        if(!order.isPresent())
            throw new NotFoundException("Can not found order");
        orderRepo.delete(order.get());
    }

    @Override
    public double calcPaymentPrice(RequestOrder dto){

        Optional<Coupon> coupon = couponRepo.findByCode(dto.getCouponCode());

        double sum = 0;

        for(String id : dto.getOrderDetailList()){
            Optional<Course> course = coursesRepo.findById(id);
            if(!course.isPresent())
                throw new NotFoundException("Can not found course id" + id);
            sum += course.get().getPrice();
        }

        if(coupon.isPresent()){
            double discount = coupon.get().getValue();
            String type = coupon.get().getType();
            if(type.equals("%"))
                sum = sum - sum*(discount/100);
            else
                sum = sum - discount;
        }
        return sum;
    }

    @Override
    public boolean checkInforFromVnpay(String vnp_txtRef, String vnp_amount){
        Optional<Order> order = orderRepo.findById(vnp_txtRef);
        if(!order.isPresent())
            return false;
        if(order.get().getPaymentPrice() == Double.valueOf(Integer.valueOf(vnp_amount)/100))
            return true;
        return false;
    }

    @Override
    public void handleActiveOrder(String orderId){
        Optional<Order> order = orderRepo.findById(orderId);
        if(!order.isPresent()){
            throw new NotFoundException("Order not found");
        }
        if(order.get().isActive())
            throw new RuntimeException("order was paid before");

        order.get().setActive(true);
        /*
            decrease remain qty of coupon
         */
        if(order.get().getCoupon() != null){
            int remainNum = order.get().getCoupon().getNumberOfRemain() -1;
            order.get().getCoupon().setNumberOfRemain(remainNum);
        }

        /*
            increase students in course
         */
        for(OrderDetail item : order.get().getOrderDetailList()){
            int currentNum = item.getCourse().getNumStudents();
            item.getCourse().setNumStudents(currentNum + 1);
        }

        /*
            delete courses in user's cart
         */

        List<String> ids = order.get().getOrderDetailList().stream()
                            .map(p -> p.getCourse().getId())
                                .collect(Collectors.toList());
        cartService.deleteFromOrder(order.get().getAccount().getUsername(), ids);

        for(OrderDetail item : order.get().getOrderDetailList()){
            CoursePaid paid = new CoursePaid();
            paid.setAccount(item.getAccount());
            paid.setCourse(item.getCourse());
            paid.setBuyDate(new Date());
            coursePaidRepo.save(paid);
        }

        orderRepo.save(order.get());
    }

    @Override
    public ResponseObject getAllActiveOrder(Optional<Integer> page, Optional<Integer> limit){
        Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(10));
        Page<Order> orders = orderRepo.findActiveOrders(pageable);
        Page<DetailOrder> dtos = orders.map(new Function<Order, DetailOrder>() {
            @Override
            public DetailOrder apply(Order order) {
                List<CourseDTO> courseDTOS = new ArrayList<>();
                for(OrderDetail item : order.getOrderDetailList()){
                    CourseDTO courseDTO = courseConverter.entityToCourseDTO(item.getCourse());
                    courseDTO.setPrice(item.getPrice());
                    courseDTOS.add(courseDTO);
                }
                DetailOrder detail = converter.orderToDetailOrder(order);
                detail.setOrderDetailList(courseDTOS);
                return detail;
            }
        });

        return new ResponseObject(dtos);
    }

    @Override
    public ResponseObject getDetailOrderById(String orderId){
        Optional<Order> order = orderRepo.findById(orderId);
        if(!order.isPresent())
            throw new NotFoundException("Can not found order id "+ orderId);

        DetailOrder dto = converter.orderToDetailOrder(order.get());
        List<CourseDTO> coursesPaids = new ArrayList<>();
        for(OrderDetail item : order.get().getOrderDetailList()){
            CourseDTO courseDTO = courseConverter.entityToCourseDTO(item.getCourse());
            courseDTO.setPrice(item.getPrice());
            coursesPaids.add(courseDTO);
        }
        dto.setOrderDetailList(coursesPaids);
        return new ResponseObject(dto);
    }


}
