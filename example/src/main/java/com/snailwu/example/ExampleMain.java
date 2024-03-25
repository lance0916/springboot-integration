package com.snailwu.example;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author WuQinglong
 * @since 2023/3/2 09:49
 */
public class ExampleMain {

    public static void main(String[] args) {
        String s = System.currentTimeMillis() + RandomStringUtils.random(10);
        System.out.println(s.length());

        System.out.println(RandomStringUtils.randomAlphabetic(32));
        System.out.println(RandomStringUtils.random(32, true, true));

    }
}
