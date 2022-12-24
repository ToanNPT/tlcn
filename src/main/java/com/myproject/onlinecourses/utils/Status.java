package com.myproject.onlinecourses.utils;

public enum Status {
    OPEN ("OPENING"),
    ACCEPT ("ACCEPTED"),
    REJECT ("REJECTED");

    public final String value;

    Status(String value) {
        this.value = value;
    }

}
