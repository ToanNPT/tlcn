package com.myproject.onlinecourses.utils;

import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utils {

    static List<String> defaultAvatars = List.of("https://users-avatars-online-courses.s3.us-west-2.amazonaws.com/man-1.png",
            "https://users-avatars-online-courses.s3.us-west-2.amazonaws.com/man-2.png",
            "https://users-avatars-online-courses.s3.us-west-2.amazonaws.com/woman-1.png",
            "https://users-avatars-online-courses.s3.us-west-2.amazonaws.com/woman-2.png");

    public static String getBaseURL(HttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();
        StringBuffer url =  new StringBuffer();
        url.append(scheme).append("://").append(serverName);
        if ((serverPort != 80) && (serverPort != 443)) {
            url.append(":").append(serverPort);
        }
        url.append(contextPath);
        if(url.toString().endsWith("/")){
            url.append("/");
        }
        return url.toString();
    }

    static public String randomAvatarUser(String gender){
        Random random = new Random();
        int index = 0;
        if(gender.equals("nam")){
            index = (int)(Math.random() * (1 - 0 + 1) + 0);
        }
        else{
            index = (int)(Math.random() * (3 - 2 + 1) + 2);
        }
        return defaultAvatars.get(index);
    }


}
