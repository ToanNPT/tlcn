package com.myproject.onlinecourses.paypal;

import com.myproject.onlinecourses.entity.Order;
import com.myproject.onlinecourses.mail.Mail;
import com.myproject.onlinecourses.mail.MailService;
import com.myproject.onlinecourses.repository.AccountRepository;
import com.myproject.onlinecourses.repository.OrderRepository;
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

import java.util.Optional;

import static com.myproject.onlinecourses.paypal.PaypalController.URL_PAYPAL_CANCEL;

@Controller
public class ResultController {

    @Autowired
    OrderService orderService;
    @Autowired
    PayPalService payPalService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    MailService mailService;

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
                Optional<Order> order = orderRepository.findById(paymentId);
                Mail mail = mailService.createConfirmOrderMail(order.get(),
                        order.get().getAccount(), "CONFIRM ORDER");
                mailService.sendMail(mail, "confirm-order-mail");
                return "success";
            }
        } catch (PayPalRESTException e) {
            //log.error(e.getMessage());
        }
        return "redirect:/";
    }
}
