package com.myproject.onlinecourses.utils;

public class SecurityConstants {
    public static final String SECRET = "toandeptrai";
    public static final long EXPIRATION_TIME = 60 * 60 * 24  * 1000; // 1 day
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/api/v1/login";
}

