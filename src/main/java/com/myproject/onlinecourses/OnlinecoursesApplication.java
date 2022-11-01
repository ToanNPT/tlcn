package com.myproject.onlinecourses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableConfigurationProperties
@SpringBootApplication
@EnableAsync
public class OnlinecoursesApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlinecoursesApplication.class, args);
	}

}
