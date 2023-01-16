package com.myproject.onlinecourses.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Array;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;

@RestController
@RequestMapping("/api/v1")

public class StreamingController {

    @Autowired
    ResourceLoader loader;

    private static String testUrl = "https://toannpt-onlinecourses.s3.amazonaws.com/toanpt-COU024-30-B%C3%A0i+1%3A+T%E1%BB%AB+v%E1%BB%B1ng";

    @GetMapping(value = "test", produces = "video/mp4")
    public Mono<Resource> testController( @RequestHeader("Range") String range){
        System.out.println("range in bytes() : " + range);
        return Mono.fromSupplier(() -> loader.getResource(testUrl));
    }

}
