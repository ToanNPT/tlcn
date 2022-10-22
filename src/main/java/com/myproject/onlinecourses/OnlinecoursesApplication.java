package com.myproject.onlinecourses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@EnableConfigurationProperties
@SpringBootApplication
public class OnlinecoursesApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlinecoursesApplication.class, args);
	}

}
