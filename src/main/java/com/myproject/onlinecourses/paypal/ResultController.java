package com.myproject.onlinecourses.paypal;

import com.myproject.onlinecourses.service.OrderService;
import com.myproject.onlinecourses.utils.Utils;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.myproject.onlinecourses.paypal.PaypalController.URL_PAYPAL_CANCEL;

@Controller
public class ResultController {

    @Autowired
    OrderService orderService;
    @Autowired
    PayPalService payPalService;

    private Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/pay/cancel")
    public String cancelPay(){
        return "cancel";
    }

    @GetMapping("/pay/success")
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId){
        try {
            Payment payment = payPalService.executePayment(paymentId, payerId);
            log.info("Access to uri success " + payment);
            if(payment.getState().equals("approved")){
                System.out.println(payment);
                orderService.activeOrder(paymentId);
                return "success";
            }
        } catch (PayPalRESTException e) {
            //log.error(e.getMessage());
        }
        return "redirect:/";
    }
}
