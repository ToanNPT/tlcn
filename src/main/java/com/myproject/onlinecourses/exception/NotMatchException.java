package com.myproject.onlinecourses.exception;

public class NotMatchException extends RuntimeException{
    public NotMatchException() {
    }

    public NotMatchException(String message) {
        super(message);
    }
}
