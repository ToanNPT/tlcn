package com.myproject.onlinecourses.paypal;

import com.myproject.onlinecourses.entity.Order;
import com.myproject.onlinecourses.mail.Mail;
import com.myproject.onlinecourses.mail.MailService;
import com.myproject.onlinecourses.repository.AccountRepository;
import com.myproject.onlinecourses.repository.OrderRepository;
import com.myproject.onlinecourses.service.CoursePaidService;
import com.myproject.onlinecourses.service.OrderService;
import com.myproject.onlinecourses.utils.Utils;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
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

    @Autowired
    CoursePaidService coursePaidService;

    private Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/pay/cancel")
    public void cancelPay(HttpServletResponse res){
        try {
            res.sendRedirect("http://localhost:3000/purcharse/fail");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/pay/success")
    public void successPay(@RequestParam("paymentId") String paymentId,
                             @RequestParam("PayerID") String payerId,
                             HttpServletResponse res) throws IOException {
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
                res.sendRedirect("http://localhost:3000/purcharse/success");
            }
        } catch (PayPalRESTException | IOException e) {
            //log.error(e.getMessage());
            res.sendRedirect("http://localhost:3000/purcharse/fail");
        }
    }

    @GetMapping("test/order")
    public String testHtml( Model model){
        Optional<Order> order = orderRepository.findById("PAYID-MN5OOOQ43771141XK6013813");

        model.addAttribute("username", order.get().getAccount().getUserDetail().getFullname());
        model.addAttribute("signature", "From FPT_TRAINING_HCMUTE");
        model.addAttribute("WEBSITE_NAME", "OnlineCourses");
        model.addAttribute("total", order.get().getTotalPrice());
        System.out.println(order.get().getTotalPrice() - order.get().getPaymentPrice());
        model.addAttribute("discount", String.format("%.2f", order.get().getTotalPrice() - order.get().getPaymentPrice()));
        model.addAttribute("paymentPrice", order.get().getPaymentPrice());
        model.addAttribute("orderList",order.get().getOrderDetailList());
        SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
        model.addAttribute("order_date", formate.format(order.get().getCreateDate()));
        model.addAttribute("orderId", order.get().getId());
        model.addAttribute("payment_type", order.get().getPayment().getName());
        model.addAttribute("user", order.get().getAccount().getUsername());
        model.addAttribute("fullname", order.get().getAccount().getUserDetail().getFullname());

        return "confirm-order-mail";
    }
}
