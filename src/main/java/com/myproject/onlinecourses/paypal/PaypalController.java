package com.myproject.onlinecourses.paypal;


import com.myproject.onlinecourses.dto.RequestOrder;
import com.myproject.onlinecourses.dto.ResponseObject;
import com.myproject.onlinecourses.entity.Coupon;
import com.myproject.onlinecourses.entity.Order;
import com.myproject.onlinecourses.repository.CouponRepository;
import com.myproject.onlinecourses.repository.OrderRepository;
import com.myproject.onlinecourses.service.OrderService;
import com.myproject.onlinecourses.utils.Utils;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/")
public class PaypalController {
    public static final String URL_PAYPAL_SUCCESS = "/pay/success";
    public static final String URL_PAYPAL_CANCEL = "/pay/cancel";
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    private PayPalService paypalService;

    @Autowired
    private OrderService orderService;

    @Autowired
    CouponRepository couponRepo;

    @PostMapping("/pay/{username}")
    public ResponseObject pay(HttpServletRequest request, @RequestBody RequestOrder dto,
                              @PathVariable("username") String username){
        String cancelUrl = Utils.getBaseURL(request) + URL_PAYPAL_CANCEL;
        String successUrl = Utils.getBaseURL(request)  + URL_PAYPAL_SUCCESS;
        boolean isValid = orderService.checkRequestOrder(dto);
        if(!isValid)
            return new ResponseObject("500", "200", "something error", null);

        Optional<Coupon> coupon = couponRepo.findByCode(dto.getCouponCode());

        double sum = dto.getOrderDetailList().stream()
                .reduce(0.0, (sub, el) -> sub + el.getPrice(), Double::sum);

        if(coupon.isPresent()){
            double discount = coupon.get().getValue();
            String type = coupon.get().getType();
            if(type == "%")
                sum = sum + sum*(discount/100);
            else
                sum = sum - discount;
        }

        try {
            Payment payment = paypalService.createPayment(
                    sum,
                    "VND",
                    PaypalPaymentMethod.paypal,
                    PaypalPaymentIntent.sale,
                    "Purchase online course",
                    cancelUrl,
                    successUrl);
            for(Links links : payment.getLinks()){
                if(links.getRel().equals("approval_url")){
                    orderService.addUnActiveOrder(payment.getId(), dto);
                    return new ResponseObject(links.getHref());
                }
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return new ResponseObject("500", "200", "Something error", null);
    }




}
