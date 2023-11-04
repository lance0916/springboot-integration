package com.example.demo;

import java.util.LinkedList;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @author WuQinglong
 * @since 2023/2/17 14:34
 */
@Slf4j
public class ProducerConsumer {

    private static final int SIZE = 5;
    private static final LinkedList<Integer> QUEUE = new LinkedList<>();
    private static final Object LOCK_OBJ = new Object();
    private static final Object PRODUCE_OBJ = new Object();
    private static final Object CONSUME_OBJ = new Object();

    public static void main(String[] args) {
//        new Thread(ProducerConsumer::produce1, "produce").start();
//        new Thread(ProducerConsumer::consume1, "consume").start();

        new Thread(ProducerConsumer::produce2, "produce").start();
        new Thread(ProducerConsumer::consume2, "consume").start();
    }

    @SneakyThrows
    public static void produce2() {
        while (true) {
            synchronized (LOCK_OBJ) {
                if (QUEUE.size() >= SIZE) {
                    log.info("队列满了，不再生产消息了");
                    LOCK_OBJ.wait();
                }

                int i = RandomUtil.randomInt(1, 100);
                QUEUE.addLast(i);
                log.info("生产一个消息:" + i);
                LOCK_OBJ.notifyAll();
                ThreadUtil.sleep(RandomUtil.randomInt(50, 500));
            }
        }
    }

    @SneakyThrows
    public static void consume2() {
        while (true) {
            synchronized (LOCK_OBJ) {
                if (QUEUE.isEmpty()) {
                    log.info("队列空了，没有消息可以消费了");
                    LOCK_OBJ.wait();
                }

                Integer i = QUEUE.removeFirst();
                log.info("消费一个消息:" + i);
                LOCK_OBJ.notifyAll();
                ThreadUtil.sleep(RandomUtil.randomInt(50, 500));
            }
        }
    }

    public static void produce1() {
        while (true) {
            if (QUEUE.size() >= SIZE) {
                ThreadUtil.sleep(100);
                continue;
            }
            synchronized (LOCK_OBJ) {
                int i = RandomUtil.randomInt(1, 100);
                QUEUE.addLast(i);
                log.info("生产一个消息:" + i);
                ThreadUtil.sleep(RandomUtil.randomInt(50, 200));
            }
        }
    }

    public static void consume1() {
        while (true) {
            if (QUEUE.isEmpty()) {
                ThreadUtil.sleep(100);
                continue;
            }
            synchronized (LOCK_OBJ) {
                Integer i = QUEUE.removeFirst();
                log.info("消费一个消息:" + i);
                ThreadUtil.sleep(RandomUtil.randomInt(50, 200));
            }
        }
    }

}
