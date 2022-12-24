package com.myproject.onlinecourses.service.impl;

import com.myproject.onlinecourses.dto.ResponseObject;
import com.myproject.onlinecourses.dto.TeacherRegisterForm;
import com.myproject.onlinecourses.entity.Account;
import com.myproject.onlinecourses.entity.RegisterTeacherForm;
import com.myproject.onlinecourses.entity.Role;
import com.myproject.onlinecourses.exception.NotFoundException;
import com.myproject.onlinecourses.mail.Mail;
import com.myproject.onlinecourses.mail.MailService;
import com.myproject.onlinecourses.repository.AccountRepository;
import com.myproject.onlinecourses.repository.RegisterTeacherRepository;
import com.myproject.onlinecourses.repository.RoleRepository;
import com.myproject.onlinecourses.security.Roles;
import com.myproject.onlinecourses.service.RegisterTeacherService;
import com.myproject.onlinecourses.utils.Status;
import org.hibernate.persister.entity.Loadable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Service
public class RegisterTeacherServiceImpl implements RegisterTeacherService {

    @Autowired
    RegisterTeacherRepository repository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    MailService mailService;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public ResponseObject insertRequest(String username, TeacherRegisterForm form) {
        Optional<Account> account = accountRepository.findById(username);
        if (!account.isPresent())
            throw new NotFoundException("Account is not found");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String currentTime = formatter.format(new Date());

        RegisterTeacherForm request = new RegisterTeacherForm();
        request.setAccount(account.get());
        request.setCurrentJob(form.getCurrentJob());
        request.setAddress(form.getAddress());
        request.setEmail(form.getEmail());
        request.setPhone(form.getPhone());
        request.setTeachingTopic(form.getTeachingTopic());
        request.setExpDescribe(form.getExpDescribe());
        request.setSubmitTime(currentTime);
        request.setStatus(Status.OPEN.value);

        Mail mail = mailService.createMailInfo(account.get(), "YÊU CẦU GIẢNG DẠY TẠI ONLINE-COURSES");
        mailService.sendMail(mail, "send-info-request-teacher");

        return new ResponseObject(repository.save(request));
    }

    @Override
    public ResponseObject getAllRequest(Optional<Integer> page, Optional<Integer> limit){
        Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(10));
        Page<RegisterTeacherForm> result = repository.findAll(pageable);
        return new ResponseObject(result);
    }

    @Override
    public ResponseObject acceptRequest(String username, Integer requestId){
        Optional<RegisterTeacherForm> existedRequest = repository.findById(requestId);
        if(!existedRequest.isPresent())
            throw new NotFoundException("Can not found submitted form");

        Optional<Account> account = accountRepository.findById(existedRequest.get().getAccount().getUsername());
        Optional<Role> role = roleRepository.findById(Roles.TEACHER.value);
        account.get().setRole(role.get());
        accountRepository.save(account.get());

        existedRequest.get().setStatus(Status.ACCEPT.value);
        RegisterTeacherForm updatedRequest = repository.save(existedRequest.get());

        Mail mail = mailService.createMailInfo(account.get(),
                "YÊU CẦU TRỞ THÀNH GIẢNG VIÊN TẠI ONLINE-COURSES THÀNH CÔNG");
        mailService.sendMail(mail, "accept-to-a-teacher");
        return new ResponseObject(updatedRequest);
    }

    @Override
    public ResponseObject rejectRequest(String username, Integer requestId){
        Optional<Account> account = accountRepository.findById(username);
        Mail mail = mailService.createMailInfo(account.get(),
                "YÊU CẦU TRỞ THÀNH GIẢNG VIÊN TẠI ONLINE-COURSES KHÔNG THÀNH CÔNG");
        Optional<RegisterTeacherForm> existedRequest = repository.findById(requestId);
        if(!existedRequest.isPresent())
            throw new NotFoundException("Can not found submitted form");

        existedRequest.get().setStatus(Status.REJECT.value);
        repository.save(existedRequest.get());
        mailService.sendMail(mail, "reject-request-teacher");

        return new ResponseObject(existedRequest);
    }

    @Override
    public ResponseObject findRequestsByStatus(Status status, Optional<Integer> page,
                                               Optional<Integer> limit){
        Page<RegisterTeacherForm> requests = null;
        Pageable  pageable = PageRequest.of(page.orElse(0), limit.orElse(10));
        if(status.value.equals(Status.ACCEPT.value)){
            requests = repository.findRequestsByStatus(Status.ACCEPT.value, pageable);
        }
        else if(status.value.equals(Status.OPEN.value)){
            requests = repository.findRequestsByStatus(Status.OPEN.value, pageable);
        }
        else if(status.value.equals(Status.REJECT.value)){
            requests = repository.findRequestsByStatus(Status.REJECT.value, pageable);
        }
        else {
            requests = repository.findAll(pageable);
        }
        return new ResponseObject(requests);

    }


}
