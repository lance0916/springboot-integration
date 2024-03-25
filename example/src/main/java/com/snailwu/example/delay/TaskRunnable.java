package com.snailwu.example.delay;

import cn.hutool.core.thread.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author WuQinglong
 * @date 2023/12/7 4:47 PM
 */
public class TaskRunnable implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskRunnable.class);

    private final DelayTask task;

    public TaskRunnable(DelayTask task) {
        this.task = task;
    }

    @Override
    public void run() {
        // TODO: 发送 HTTP 请求
        LOGGER.info("发送 HTTP 请求: " + task);

        ThreadUtil.sleep(5, TimeUnit.SECONDS);

        LOGGER.info("发送 HTTP 请求 END: " + task);

    }
}
