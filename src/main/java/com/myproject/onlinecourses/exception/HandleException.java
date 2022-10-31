package com.myproject.onlinecourses.exception;

import com.myproject.onlinecourses.dto.ResponseObject;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class HandleException {
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ResponseObject handleNotFound(NotFoundException e){
        return new ResponseObject("400", "200", e.getMessage(), null);
    }

    @ExceptionHandler(DuplicateException.class)
    @ResponseBody
    public ResponseObject handleDuplicate(DuplicateException e){
        return new ResponseObject("400", "200", e.getMessage(), null);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseObject handleDuplicate(RuntimeException e){
        return new ResponseObject("400", "200", e.getMessage(), null);
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseBody
    public ResponseObject handleForbidden(){
        return new ResponseObject("400", "200", "You don't have exact permission to access our sources", null);
    }

    @ExceptionHandler(NotMatchException.class)
    @ResponseBody
    public ResponseObject handleNotMatches(NotMatchException e){
        return new ResponseObject("400", "200", e.getMessage(), null);
    }

}
