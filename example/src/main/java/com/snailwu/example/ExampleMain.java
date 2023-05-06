package com.snailwu.example;

import cn.hutool.core.date.DateUtil;

import java.util.Date;

/**
 * @author WuQinglong
 * @since 2023/3/2 09:49
 */
public class ExampleMain {

    public static void main(String[] args) {

        Date referenceDate = new Date(1600617600000L);
        System.out.println(DateUtil.formatDateTime(referenceDate));

    }

}
