package com.xrervip.super_ai_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class GeneralAiServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeneralAiServiceApplication.class, args);
    }

}
