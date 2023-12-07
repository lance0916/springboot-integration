package com.snailwu.example;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.util.List;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetAddress;

@SpringBootApplication
public class ExampleApplication implements ApplicationRunner {

    private final List<MemoryPoolMXBean> memoryPoolMXBeans = ManagementFactory.getMemoryPoolMXBeans();

    public static void main(String[] args) {
        SpringApplication.run(ExampleApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        String hostName = InetAddress.getLocalHost().getHostName();
//
//        for (MemoryPoolMXBean mxBean : memoryPoolMXBeans) {
////            String[] memoryManagerNames = mxBean.getMemoryManagerNames();
////            for (String memoryManagerName : memoryManagerNames) {
////                System.out.println(memoryManagerName);
////            }
//
//            System.out.println(mxBean.getName());
//
//        }

    }

}
