package com.myproject.onlinecourses.service.impl;

import com.myproject.onlinecourses.converter.CartConverter;
import com.myproject.onlinecourses.converter.CourseConverter;
import com.myproject.onlinecourses.dto.CartDto;
import com.myproject.onlinecourses.dto.CourseDTO;
import com.myproject.onlinecourses.dto.ResponseObject;
import com.myproject.onlinecourses.entity.Account;
import com.myproject.onlinecourses.entity.Cart;
import com.myproject.onlinecourses.entity.CartDetail;
import com.myproject.onlinecourses.entity.Course;
import com.myproject.onlinecourses.exception.DuplicateException;
import com.myproject.onlinecourses.exception.NotFoundException;
import com.myproject.onlinecourses.repository.AccountRepository;
import com.myproject.onlinecourses.repository.CartDetailRepository;
import com.myproject.onlinecourses.repository.CartRepository;
import com.myproject.onlinecourses.repository.CoursesRepository;
import com.myproject.onlinecourses.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    AccountRepository accountRepo;

    @Autowired
    CartRepository cartRepo;

    @Autowired
    CartDetailRepository cartDetailRepo;

    @Autowired
    CoursesRepository coursesRepo;

    @Autowired
    CartConverter converter;

    @Autowired
    CourseConverter courseConverter;

    @Override
    public ResponseObject getCartDetailById(Integer id){
        Optional<CartDetail> cartDetail = cartDetailRepo.findById(id);
        if(!cartDetail.isPresent())
            throw new NotFoundException("Can not find cart detail by id "+ id);
        return new ResponseObject(converter.entityToCartDetailDTO(cartDetail.get()));
    }

    @Override
    public ResponseObject getByUsername(String username){
        Optional<Account> account = accountRepo.findById(username);
        if(!account.isPresent()) throw new NotFoundException("Can not found username: " + username);
        Optional<Cart> cart = cartRepo.findById(username);
        List<CartDetail> cartDetails = cart.get().getCartDetailList();

        CartDto dto = converter.entityToCartDTO(cart.get());
        dto.setCartDetailList(cartDetails.stream()
                .map(c -> courseConverter.entityToCourseDTO(c.getCourse()))
                .collect(Collectors.toList())
        );
        return new ResponseObject(dto);
    }

    @Override
    public ResponseObject delete(String id, String username){
        Optional<Account> account = accountRepo.findById(username);
        if(!account.isPresent()) throw new NotFoundException("Can not found username: " + username);
        Optional<CartDetail> cartDetail = cartDetailRepo.getFirstByUsernameAndCourseId(username, id);
        if(!cartDetail.isPresent()) throw new NotFoundException("Can not found your item");
        if(account.get().getUsername() != cartDetail.get().getCart().getUsername())
            throw new NotFoundException("Can not delete cart, something wrong");
        Optional<Cart> cart = cartRepo.findById(username);
        double total = cart.get().getTotalPrice() - cartDetail.get().getCourse().getPrice();
        cart.get().setTotalPrice(Math.round(total*100)/100.00);
        cart.get().setPaymentPrice(Math.round(total*100)/100.00);
        cartDetailRepo.delete(cartDetail.get());
        cartRepo.save(cart.get());
        CourseDTO courseDTO = courseConverter.entityToCourseDTO(cartDetail.get().getCourse());

        return new ResponseObject("", "200", "Delete successful", courseDTO);
    }

    @Override
    public ResponseObject add(String username, String courseId){
        Optional<Cart> cart = cartRepo.findById(username);
        if(!cart.isPresent()) throw new NotFoundException("Can not found your cart : " + username);
        Optional<Course> course = coursesRepo.findById(courseId);
        if(!course.isPresent()) throw new NotFoundException("Can not found this course : " + courseId);

        Optional<Course> foundCourse = cartRepo.checkExitedCourseInCart(courseId, username);
        if(foundCourse.isPresent())
            throw new DuplicateException("course is exited in your cart");

        CartDetail cartDetail = new CartDetail();
        cartDetail.setCart(cart.get());
        cartDetail.setCourse(course.get());
        double total = cart.get().getTotalPrice() + course.get().getPrice();
        CartDetail res = cartDetailRepo.save(cartDetail);
        cart.get().setTotalPrice(total);
        cart.get().setPaymentPrice(total);
        cartRepo.save(cart.get());
        return new ResponseObject(converter.entityToCartDetailDTO(res));
    }

}
