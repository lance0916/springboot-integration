package com.snailwu.example.delay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author WuQinglong
 * @date 2023/12/7 4:31 PM
 */
public class TaskManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskManager.class);

    /**
     * 等待执行的任务
     */
    private DelayTask task;

    /**
     * 任务执行延时时间
     */
    private final long delayTime;

    /**
     * 发送 HTTP 请求线程池
     */
    private final ThreadPoolExecutor executor;

    /**
     * 选择任务的线程池
     */
    private final ThreadPoolExecutor selectTaskSinglePool;

    /**
     * 任务锁
     */
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition available = lock.newCondition();

    /**
     * @param delayTime 任务执行延时时间
     */
    public TaskManager(long delayTime) {
        this.delayTime = delayTime;
        this.executor = new ThreadPoolExecutor(10, 10, 1, TimeUnit.HOURS, new SynchronousQueue<>(),
            new ThreadPoolExecutor.CallerRunsPolicy());

        this.selectTaskSinglePool = new ThreadPoolExecutor(1, 1, 0, TimeUnit.HOURS, new ArrayBlockingQueue<>(1));
        this.selectTaskSinglePool.submit(new TakeRunnable(this));
    }

    /**
     * 设置当前的任务
     */
    public void setTask(DelayTask task) {
        lock.lock();
        try {
            this.task = task;
            LOGGER.info("添加任务: " + task);
            available.signal();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 任务生存时间
     */
    private long liveTime() {
        return System.currentTimeMillis() - task.getCreateTime();
    }

    /**
     * 执行任务
     */
    public void executeTask(DelayTask delayTask) {
        executor.submit(new TaskRunnable(delayTask));
    }

    /**
     * 获取当前的任务
     * 若没有任务，一直等着
     */
    public DelayTask take() throws InterruptedException {
        lock.lockInterruptibly();
        try {
            while (true) {
                final DelayTask task = this.task;
                if (task == null) {
                    LOGGER.info("没有任务，等待中...");
                    available.await();
                } else {
                    long liveTime = liveTime();
                    if (liveTime > delayTime) {
                        LOGGER.info("获取到任务: " + task);
                        this.task = null;
                        return task;
                    }

                    long awaitMillis = delayTime - liveTime;
                    LOGGER.info("等待中... " + task);
                    available.await(awaitMillis, TimeUnit.MILLISECONDS);
                }
            }
        } finally {
            lock.unlock();
        }
    }

}
