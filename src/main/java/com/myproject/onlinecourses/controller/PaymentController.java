package com.myproject.onlinecourses.controller;

import com.myproject.onlinecourses.dto.PaymentDTO;
import com.myproject.onlinecourses.dto.ResponseObject;
import com.myproject.onlinecourses.dto.ReviewDTO;
import com.myproject.onlinecourses.service.PaymentService;
import com.myproject.onlinecourses.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/")
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    @GetMapping("payments")
    public ResponseObject getAll(){
        return paymentService.getAll();
    }

    @GetMapping("payment/{id}")
    public ResponseObject getByID(@PathVariable("id") String id){
        return paymentService.getById(id);
    }


    @PostMapping("payment/add")
    public ResponseObject save(@RequestBody PaymentDTO dto){
        return paymentService.savePayment(dto);
    }

    @DeleteMapping("payment/delete/{id}")
    public ResponseObject delete(@PathVariable("id") String id){
        return paymentService.deleteById(id);
    }

    @PutMapping("payment/update/{id}")
    public ResponseObject update(@PathVariable("id") String id, @Validated @RequestBody PaymentDTO dto,
                                 BindingResult bindingResult){
        if(bindingResult.hasErrors())
            throw new RuntimeException(bindingResult.getFieldError().getDefaultMessage());
        return paymentService.updatePayment(id, dto);
    }

}
