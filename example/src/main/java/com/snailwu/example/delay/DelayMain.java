package com.snailwu.example.delay;

import cn.hutool.core.thread.NamedThreadFactory;
import cn.hutool.core.thread.ThreadUtil;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author WuQinglong
 * @date 2023/12/7 5:40 PM
 */
public class DelayMain {

    public static void main(String[] args) {
//        TaskManager manager = new TaskManager(1000);
//        manager.setTask(new DelayTask("T-1"));
//
//        ThreadUtil.sleep(1, TimeUnit.SECONDS);
//        manager.setTask(new DelayTask("T-2"));
//
//        ThreadUtil.sleep(2, TimeUnit.SECONDS);
//        manager.setTask(new DelayTask("T-3"));
//
//        ThreadUtil.sleep(1, TimeUnit.HOURS);

        ThreadPoolExecutor selectTaskSinglePool = new ThreadPoolExecutor(1, 1, 0, TimeUnit.HOURS,
            new ArrayBlockingQueue<>(1), new NamedThreadFactory("select-", true));
        selectTaskSinglePool.submit(() -> {
            while (true) {
                System.out.println("111");
            }
        });

    }

}
