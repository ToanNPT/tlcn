package com.myproject.onlinecourses.utils;

import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Component;

@Component
public class Generator {
    public String generateToken() {
        return RandomString.make(50);
    }
}
