package com.myproject.onlinecourses.service;

import com.myproject.onlinecourses.dto.PaymentDTO;
import com.myproject.onlinecourses.dto.ResponseObject;

public interface PaymentService {
    ResponseObject getAll();

    ResponseObject getById(String id);

    ResponseObject deleteById(String id);

    ResponseObject savePayment(PaymentDTO dto);

    ResponseObject updatePayment(String id, PaymentDTO dto);
}
