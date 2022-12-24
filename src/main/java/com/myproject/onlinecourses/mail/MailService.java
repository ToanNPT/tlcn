package com.myproject.onlinecourses.mail;

import com.myproject.onlinecourses.entity.Account;
import com.myproject.onlinecourses.entity.Order;
import org.springframework.scheduling.annotation.Async;

import javax.mail.internet.MimeMessage;

public interface MailService {

    Mail createTokenMail(Account account, String subject, String token);

    Mail createMailInfo(Account account, String subject);

    Mail createConfirmOrderMail(Order order, Account account, String subject);

    MimeMessage prepareMail(Mail mail, String templatePath);

    @Async("mailExecutor")
    void sendMail(Mail mail, String templatePath);
}
