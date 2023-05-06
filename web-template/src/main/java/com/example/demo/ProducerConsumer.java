package com.example.demo;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

/**
 * @author WuQinglong
 * @since 2023/2/17 14:34
 */
@Slf4j
public class ProducerConsumer {

    private static final int size = 5;
    private static final LinkedList<Integer> queue = new LinkedList<>();
    private static final Object lockObj = new Object();
    private static final Object produceObj = new Object();
    private static final Object consumeObj = new Object();

    public static void main(String[] args) {
//        new Thread(ProducerConsumer::produce1, "produce").start();
//        new Thread(ProducerConsumer::consume1, "consume").start();

        new Thread(ProducerConsumer::produce2, "produce").start();
        new Thread(ProducerConsumer::consume2, "consume").start();
    }

    @SneakyThrows
    public static void produce2() {
        while (true) {
            synchronized (lockObj) {
                if (queue.size() >= size) {
                    log.info("队列满了，不再生产消息了");
                    lockObj.wait();
                }

                int i = RandomUtil.randomInt(1, 100);
                queue.addLast(i);
                log.info("生产一个消息:" + i);
                lockObj.notifyAll();
                ThreadUtil.sleep(RandomUtil.randomInt(50, 500));
            }
        }
    }

    @SneakyThrows
    public static void consume2() {
        while (true) {
            synchronized (lockObj) {
                if (queue.isEmpty()) {
                    log.info("队列空了，没有消息可以消费了");
                    lockObj.wait();
                }

                Integer i = queue.removeFirst();
                log.info("消费一个消息:" + i);
                lockObj.notifyAll();
                ThreadUtil.sleep(RandomUtil.randomInt(50, 500));
            }
        }
    }

    public static void produce1() {
        while (true) {
            if (queue.size() >= size) {
                ThreadUtil.sleep(100);
                continue;
            }
            synchronized (lockObj) {
                int i = RandomUtil.randomInt(1, 100);
                queue.addLast(i);
                log.info("生产一个消息:" + i);
                ThreadUtil.sleep(RandomUtil.randomInt(50, 200));
            }
        }
    }

    public static void consume1() {
        while (true) {
            if (queue.isEmpty()) {
                ThreadUtil.sleep(100);
                continue;
            }
            synchronized (lockObj) {
                Integer i = queue.removeFirst();
                log.info("消费一个消息:" + i);
                ThreadUtil.sleep(RandomUtil.randomInt(50, 200));
            }
        }
    }

}
