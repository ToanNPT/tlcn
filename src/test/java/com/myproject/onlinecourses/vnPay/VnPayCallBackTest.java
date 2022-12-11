package com.myproject.onlinecourses.vnPay;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;

class VnPayCallBackTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void returnResultPay() {
        String urlTest = "";
        webTestClient.get()
                .uri(urlTest)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }
}