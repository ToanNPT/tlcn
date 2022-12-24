package com.myproject.onlinecourses.service;

import com.myproject.onlinecourses.dto.ResponseObject;
import com.myproject.onlinecourses.dto.TeacherRegisterForm;
import com.myproject.onlinecourses.entity.RegisterTeacherForm;
import com.myproject.onlinecourses.utils.Status;

import java.util.Optional;

public interface RegisterTeacherService {
    ResponseObject insertRequest(String username, TeacherRegisterForm form);

    ResponseObject getAllRequest(Optional<Integer> page, Optional<Integer> limit);

    ResponseObject acceptRequest(String username, Integer requestId);

    ResponseObject rejectRequest(String username, Integer requestId);

    ResponseObject findRequestsByStatus(Status status, Optional<Integer> page,
                                        Optional<Integer> limit);
}
