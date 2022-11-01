package com.myproject.onlinecourses.mail;

import com.myproject.onlinecourses.entity.Account;
import org.springframework.scheduling.annotation.Async;

import javax.mail.internet.MimeMessage;

public interface MailService {

    Mail createTokenMail(Account account, String subject, String token);

    MimeMessage prepareMail(Mail mail);

    @Async("mailExecutor")
    void sendMail(Mail mail);
}
