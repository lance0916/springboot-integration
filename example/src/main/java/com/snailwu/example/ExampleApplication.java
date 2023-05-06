package com.snailwu.example;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetAddress;

@SpringBootApplication
public class ExampleApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(ExampleApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String hostName = InetAddress.getLocalHost().getHostName();


    }

}
