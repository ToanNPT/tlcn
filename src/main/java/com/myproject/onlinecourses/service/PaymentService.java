package com.myproject.onlinecourses.service;

import com.myproject.onlinecourses.dto.PaymentDTO;
import com.myproject.onlinecourses.dto.ResponseObject;

import java.util.Optional;

public interface PaymentService {
    ResponseObject getAll(Optional<Integer> page, Optional<Integer> limit);

    ResponseObject getById(String id);

    ResponseObject deleteById(String id);

    ResponseObject savePayment(PaymentDTO dto);

    ResponseObject updatePayment(String id, PaymentDTO dto);
}
