package com.myproject.onlinecourses.controller;

import com.myproject.onlinecourses.dto.RegisterAccountForm;
import com.myproject.onlinecourses.dto.ResponseObject;
import com.myproject.onlinecourses.dto.TeacherRegisterForm;
import com.myproject.onlinecourses.exception.NotFoundException;
import com.myproject.onlinecourses.exception.NotMatchException;
import com.myproject.onlinecourses.service.RegisterTeacherService;
import com.myproject.onlinecourses.utils.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/")
public class RegisterTeacherController {

    @Autowired
    RegisterTeacherService service;

    @PostMapping("enroll-teacher")
    public ResponseObject submitRegisterTeacherForm(@RequestBody TeacherRegisterForm form,
                                                    BindingResult result,
                                                    Principal principal){
        if (result.hasErrors()) {
            var errorMess = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            throw new NotMatchException(errorMess);
        }
        if(principal == null){
            throw new NotFoundException("You must login before submit form");
        }
        String username = principal.getName();
        return service.insertRequest(username, form);
    }

    @PostMapping("accept-request-teacher/{idRequest}")
    public ResponseObject acceptRequestToATeacher(Principal principal,
                                                  @PathVariable("idRequest") Integer id){
        if(principal == null){
            throw new NotFoundException("You must login first");
        }
        String username = principal.getName();
        return service.acceptRequest(username, id);
    }

    @PostMapping("reject-request-teacher/{idRequest}")
    public ResponseObject rejectRequestToATeacher(Principal principal,
                                                  @PathVariable("idRequest") Integer id){
        if(principal == null){
            throw new NotFoundException("You must login first");
        }
        String username = principal.getName();
        return service.rejectRequest(username, id);
    }

    @GetMapping("requests-teacher/accepted")
    public ResponseObject findAcceptedRequestToATeacher(Principal principal,
                                                  @RequestParam("page") Optional<Integer> page,
                                                  @RequestParam("limit") Optional<Integer> limit){
        if(principal == null){
            throw new NotFoundException("You must login before search resources");
        }
        String username = principal.getName();
        return service.findRequestsByStatus(Status.ACCEPT, page,limit);
    }

    @GetMapping("requests-teacher/rejected")
    public ResponseObject findRejectedRequestToATeacher(Principal principal,
                                                        @RequestParam("page") Optional<Integer> page,
                                                        @RequestParam("limit") Optional<Integer> limit){
        if(principal == null){
            throw new NotFoundException("You must login before search resources");
        }
        return service.findRequestsByStatus(Status.REJECT, page,limit);
    }

    @GetMapping("requests-teacher/opening")
    public ResponseObject findOpeningRequestToATeacher(Principal principal,
                                                        @RequestParam("page") Optional<Integer> page,
                                                        @RequestParam("limit") Optional<Integer> limit){
        if(principal == null){
            throw new NotFoundException("You must login before search resources");
        }
        return service.findRequestsByStatus(Status.OPEN, page,limit);
    }
}
