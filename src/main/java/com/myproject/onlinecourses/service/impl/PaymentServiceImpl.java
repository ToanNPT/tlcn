package com.myproject.onlinecourses.service.impl;

import com.myproject.onlinecourses.converter.PaymentConverter;
import com.myproject.onlinecourses.dto.PaymentDTO;
import com.myproject.onlinecourses.dto.ResponseObject;
import com.myproject.onlinecourses.entity.Payment;
import com.myproject.onlinecourses.exception.DuplicateException;
import com.myproject.onlinecourses.exception.NotFoundException;
import com.myproject.onlinecourses.repository.PaymentRepository;
import com.myproject.onlinecourses.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    PaymentRepository paymentRepo;

    @Autowired
    PaymentConverter converter;

    @Override
    public ResponseObject getAll(Optional<Integer> page, Optional<Integer> limit){
        Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(10), Sort.by("name").ascending());
        Page<Payment> payments = paymentRepo.findAllByActive(true, pageable);
        Page<PaymentDTO> dtos = payments.map(new Function<Payment, PaymentDTO>() {
            @Override
            public PaymentDTO apply(Payment payment) {
                return converter.entityToDTO(payment);
            }
        });
        return new ResponseObject(dtos);
    }

    @Override
    public ResponseObject getById(String id){
        Optional<Payment> payment = paymentRepo.findById(id);
        if(!payment.isPresent()) throw new NotFoundException("Can not found payment");
        return new ResponseObject(converter.entityToDTO(payment.get()));
    }

    @Override
    public ResponseObject deleteById(String id){
        Optional<Payment> payment = paymentRepo.findById(id);
        if(!payment.isPresent()) throw new NotFoundException("Can not found payment");
        payment.get().setActive(false);
        paymentRepo.save(payment.get());
        return new ResponseObject("", "200", "Delete successful", null);
    }

    @Override
    public ResponseObject savePayment(PaymentDTO dto){
        Optional<Payment> found = paymentRepo.findByName(dto.getName());
        if(found.isPresent()) throw new DuplicateException("Payment name is existed");
        Payment payment = new Payment();
        payment.setName(dto.getName());
        payment.setActive(true);
        Payment res = paymentRepo.save(payment);
        return new ResponseObject(converter.entityToDTO(res));
    }

    @Override
    public ResponseObject updatePayment(String id, PaymentDTO dto){
        Optional<Payment> payment = paymentRepo.findById(id);
        if(!payment.isPresent()) throw new NotFoundException("Can not found payment");
        payment.get().setName(dto.getName());
        Payment res = paymentRepo.save(payment.get());
        return new ResponseObject(converter.entityToDTO(res));
    }
}
