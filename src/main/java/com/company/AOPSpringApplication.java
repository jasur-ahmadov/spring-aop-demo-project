package com.company;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class AOPSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(AOPSpringApplication.class, args);
    }
}