package com.example.web02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Web02Application {

    public static void main(String[] args) {
        SpringApplication.run(Web02Application.class, args);
    }


}
