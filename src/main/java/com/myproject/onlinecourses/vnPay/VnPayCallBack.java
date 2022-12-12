package com.myproject.onlinecourses.vnPay;

import com.fasterxml.jackson.annotation.OptBoolean;
import com.google.gson.Gson;
import com.myproject.onlinecourses.dto.CoursePaidReturn;
import com.myproject.onlinecourses.entity.CartDetail;
import com.myproject.onlinecourses.entity.Order;
import com.myproject.onlinecourses.entity.OrderDetail;
import com.myproject.onlinecourses.mail.Mail;
import com.myproject.onlinecourses.mail.MailService;
import com.myproject.onlinecourses.repository.CartDetailRepository;
import com.myproject.onlinecourses.repository.CartRepository;
import com.myproject.onlinecourses.repository.OrderRepository;
import com.myproject.onlinecourses.service.OrderService;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    CartDetailRepository cartDetailRepo;


    @GetMapping("vnpay/payment/return")
    public void returnResultPay(HttpServletRequest req,
                                HttpServletResponse res) throws IOException, URISyntaxException {
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
                orderService.handleActiveOrder(vnp_TxnRef);
                Optional<Order> order = orderRepository.findById(vnp_TxnRef);
                Mail mail = mailService.createConfirmOrderMail(order.get(), order.get().getAccount(), "CONFIRM ORDER");
                mailService.sendMail(mail, "confirm-order-mail");

                res.sendRedirect(buildResultUrlPayment("http://localhost:3000/purcharse/success",
                        order.get()));
            } else {
                orderService.deleteUnActiveorder(vnp_TxnRef);
                res.sendRedirect("http://localhost:3000/purcharse/fail");
            }
        }
    }
//    }

    public static String buildResultUrlPayment(String domain, Order order) throws URISyntaxException {
        if(!order.isActive())
            return "http://localhost:3000/purcharse/fail";
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

        List<CoursePaidReturn> orderItems = new ArrayList<>();
        Gson gson = new Gson();
        URIBuilder url = new URIBuilder(domain);

        url.addParameter("orderId", order.getId());
        url.addParameter("totalPrice", String.valueOf(order.getTotalPrice()));
        url.addParameter("paymentPrice", String.valueOf(order.getPaymentPrice()));
        url.addParameter("discountPrice", String.valueOf(order.getTotalPrice() - order.getPaymentPrice()));
        if(order.getCoupon() != null)
            url.addParameter("couponCode", order.getCoupon().getCode());
        url.addParameter("orderDate", format.format(order.getCreateDate()));
        url.addParameter("paymentType", order.getPayment().getName());

        for(OrderDetail o : order.getOrderDetailList()){
            orderItems.add(new CoursePaidReturn(o.getCourse().getId(),
                    o.getCourse().getName(), o.getAccount().getUsername(), o.getPrice()));
        }

        url.addParameter("orderInfo", gson.toJson(orderItems));

        return url.toString();
    }

}
