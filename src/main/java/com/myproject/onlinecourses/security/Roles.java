package com.myproject.onlinecourses.security;

public enum Roles {
    ADMIN("ADMIN"),
    USER("USER"),
    GUEST("GUEST"),
    TEACHER("TEACHER");

    public final String value;

    Roles(String value) {
        this.value = value;
    }

}
