package com.example;

import com.example.entity.UserInfo;
import com.example.entity.UserInfo.Column;
import com.example.entity.UserInfoExample;
import com.example.entity.UserInfoExample.IExampleWhen;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.lang.NonNull;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@SpringBootApplication
@EnableScheduling
@EnableRetry(proxyTargetClass = true)
public class WebTemplateApplication implements ApplicationRunner, BeanPostProcessor {

    public static void main(String[] args) {
        SpringApplication.run(WebTemplateApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Hello Web");

    }

    @Override
    public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
//        log.info("Bean: {} 初始化完毕", beanName);
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
