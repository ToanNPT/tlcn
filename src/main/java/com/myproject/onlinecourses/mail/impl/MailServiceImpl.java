package com.myproject.onlinecourses.mail.impl;

import com.myproject.onlinecourses.entity.Account;
import com.myproject.onlinecourses.entity.Order;
import com.myproject.onlinecourses.mail.Mail;
import com.myproject.onlinecourses.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    SpringTemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    String serverMail;

    @Override
    public Mail createTokenMail(Account account, String subject, String token){
        Mail mail = new Mail();
        mail.setFrom(serverMail);
        mail.setTo(account.getUserDetail().getEmail());
        mail.setSubject(subject);

        Map<String, Object> model = new HashMap<>();
        model.put("username", account.getUsername());
        model.put("signature", "From FPT_TRAINING_HCMUTE");
        model.put("WEBSITE_NAME", "DEMO_TGDD");
        String url = "http://localhost:8080/api/v1/reset-password/" + token;
        model.put("resetUrl", url );
        mail.setModel(model);
        return mail;
    }

    @Override
    public Mail createConfirmOrderMail(Order order, Account account, String subject){
        Mail mail = new Mail();
        mail.setFrom(serverMail);
        mail.setTo(account.getUserDetail().getEmail());
        mail.setSubject(subject);

        Map<String, Object> model = new HashMap<>();
        model.put("username", order.getAccount().getUserDetail().getFullname());
        model.put("signature", "From FPT_TRAINING_HCMUTE");
        model.put("WEBSITE_NAME", "OnlineCourses");
        model.put("total", order.getTotalPrice());
        System.out.println(order.getTotalPrice() - order.getPaymentPrice());
        model.put("discount", String.format("%.2f", order.getTotalPrice() - order.getPaymentPrice()));
        model.put("paymentPrice", order.getPaymentPrice());
        model.put("orderList",order.getOrderDetailList());
        SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
        model.put("order_date", formate.format(order.getCreateDate()));
        model.put("orderId", order.getId());
        model.put("payment_type", order.getPayment().getName());
        model.put("user", order.getAccount().getUsername());
        model.put("fullname", order.getAccount().getUserDetail().getFullname());

        mail.setModel(model);
        return mail;
    }

    @Override
    public MimeMessage prepareMail(Mail mail, String templatePath){
        try{
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            Context context = new Context();
            context.setVariables(mail.getModel());
            String html = templateEngine.process(templatePath, context);

            helper.setTo(mail.getTo());
            helper.setFrom(mail.getFrom());
            helper.setSubject(mail.getSubject());
            helper.setText(html, true);
            return message;
        } catch (MessagingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Async("mailExecutor")
    public void sendMail(Mail mail, String templatePath){
        MimeMessage message = prepareMail(mail, templatePath);
        javaMailSender.send(message);
    }
}
