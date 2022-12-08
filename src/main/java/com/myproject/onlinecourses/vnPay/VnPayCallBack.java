package com.myproject.onlinecourses.vnPay;

import com.fasterxml.jackson.annotation.OptBoolean;
import com.myproject.onlinecourses.entity.Order;
import com.myproject.onlinecourses.mail.Mail;
import com.myproject.onlinecourses.mail.MailService;
import com.myproject.onlinecourses.repository.OrderRepository;
import com.myproject.onlinecourses.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1/")
public class VnPayCallBack {

    @Autowired
    VnPayService vnPayService;

    @Autowired
    OrderService orderService;

    @Autowired
    MailService mailService;

    @Autowired
    OrderRepository orderRepository;


    @GetMapping("vnpay/payment/return")
    public void returnResultPay(HttpServletRequest req,
                                HttpServletResponse res) throws IOException {
//        boolean checkSum = vnPayService.checkSum(req);
//        if(!checkSum){
//            //redirect to fail payment page
//            res.sendRedirect("http://localhost:3000/purcharse/fail");
//        }
//        else{
        String vnp_amount = req.getParameter("vnp_Amount");
        String vnp_TxnRef = req.getParameter("vnp_TxnRef");
        String vnp_status = req.getParameter("vnp_ResponseCode");
        boolean checkAmount = orderService.checkInforFromVnpay(vnp_TxnRef, vnp_amount);

        if (!checkAmount) {
            //redirect to fail payment page
            res.sendRedirect("http://localhost:3000/purcharse/fail");
        } else {

            String status = req.getParameter("vnp_ResponseCode");
            if (status.equals("00")) {
                orderService.activeOrder(vnp_TxnRef);
                //redirect to success payment page

                Optional<Order> order = orderRepository.findById(vnp_TxnRef);
                Mail mail = mailService.createConfirmOrderMail(order.get(), order.get().getAccount(), "CONFIRM ORDER");
                mailService.sendMail(mail, "confirm-order-mail");
                res.sendRedirect("http://localhost:3000/purcharse/success");
            } else {
                orderService.deleteUnActiveorder(vnp_TxnRef);
                res.sendRedirect("http://localhost:3000/purcharse/fail");
            }
        }
    }
//    }

}
