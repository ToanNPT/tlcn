package com.myproject.onlinecourses.mail.impl;

import com.myproject.onlinecourses.entity.Account;
import com.myproject.onlinecourses.mail.Mail;
import com.myproject.onlinecourses.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    SpringTemplateEngine templateEngine;

    @Override
    public Mail createTokenMail(Account account, String subject, String token){
        Mail mail = new Mail();
        mail.setFrom("fpt.training.hcmute@gmail.com");
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
    public MimeMessage prepareMail(Mail mail){
        try{
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            Context context = new Context();
            context.setVariables(mail.getModel());
            String html = templateEngine.process("reset-password-mail", context);

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
    public void sendMail(Mail mail){
        MimeMessage message = prepareMail(mail);
        javaMailSender.send(message);
    }
}
