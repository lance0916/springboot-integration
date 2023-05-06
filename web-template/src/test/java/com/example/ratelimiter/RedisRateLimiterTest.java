package com.example.ratelimiter;

import cn.hutool.core.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import javax.annotation.Resource;

/**
 * @author WuQinglong
 * @since 2023/2/17 15:27
 */
@Slf4j
@SpringBootTest
class RedisRateLimiterTest {

    @Resource
    private RedisConnectionFactory redisConnectionFactory;

    @Test
    void tryAcquire1() {
        RedisRateLimiter rateLimiter = new RedisRateLimiter("api:demo", 1, redisConnectionFactory);
        long millis = System.currentTimeMillis();
        millis = millis - (millis % (1000 / 1));
        log.info("当前对齐时间戳为:" + millis);

        Assert.isTrue(rateLimiter.tryAcquire(millis));
        Assert.isFalse(rateLimiter.tryAcquire(millis + 100));
        Assert.isFalse(rateLimiter.tryAcquire(millis + 500));
        Assert.isFalse(rateLimiter.tryAcquire(millis + 601));
    }

    @Test
    void tryAcquire2() {
        RedisRateLimiter rateLimiter = new RedisRateLimiter("api:demo", 2, redisConnectionFactory);
        long millis = System.currentTimeMillis();
        millis = millis - (millis % (1000 / 2));
        log.info("当前对齐时间戳为:" + millis);

        Assert.isTrue(rateLimiter.tryAcquire(millis));
        Assert.isFalse(rateLimiter.tryAcquire(millis + 100));
        Assert.isTrue(rateLimiter.tryAcquire(millis + 500));
        Assert.isFalse(rateLimiter.tryAcquire(millis + 501));
    }

    @Test
    void tryAcquire3() {
        RedisRateLimiter rateLimiter = new RedisRateLimiter("api:demo", 3, redisConnectionFactory);
        long millis = System.currentTimeMillis();
        millis = millis - (millis % (1000 / 3));
        log.info("当前对齐时间戳为:" + millis);

        Assert.isTrue(rateLimiter.tryAcquire(millis));
        Assert.isFalse(rateLimiter.tryAcquire(millis + 332));
        Assert.isTrue(rateLimiter.tryAcquire(millis + 333));
        Assert.isFalse(rateLimiter.tryAcquire(millis + 500));
        Assert.isFalse(rateLimiter.tryAcquire(millis + 600));
        Assert.isTrue(rateLimiter.tryAcquire(millis + 666));
        Assert.isFalse(rateLimiter.tryAcquire(millis + 667));
        Assert.isFalse(rateLimiter.tryAcquire(millis + 998));
        Assert.isTrue(rateLimiter.tryAcquire(millis + 999));

        Assert.isFalse(rateLimiter.tryAcquire(millis + 1000));
    }

    @Test
    void tryAcquire4() {
        RedisRateLimiter rateLimiter = new RedisRateLimiter("api:demo", 4, redisConnectionFactory);
        long millis = System.currentTimeMillis();
        millis = millis - (millis % (1000 / 4));
        log.info("当前对齐时间戳为:" + millis);

        Assert.isTrue(rateLimiter.tryAcquire(millis));
        Assert.isFalse(rateLimiter.tryAcquire(millis + 249));
        Assert.isTrue(rateLimiter.tryAcquire(millis + 250));
        Assert.isFalse(rateLimiter.tryAcquire(millis + 251));
        Assert.isFalse(rateLimiter.tryAcquire(millis + 499));
        Assert.isTrue(rateLimiter.tryAcquire(millis + 500));
        Assert.isFalse(rateLimiter.tryAcquire(millis + 501));
        Assert.isTrue(rateLimiter.tryAcquire(millis + 750));
        Assert.isFalse(rateLimiter.tryAcquire(millis + 999));

        // 新的一秒
        Assert.isTrue(rateLimiter.tryAcquire(millis + 1000));
    }

    @Test
    void tryAcquire41() {
        RedisRateLimiter rateLimiter = new RedisRateLimiter("api:demo", 4, redisConnectionFactory);
        long millis = System.currentTimeMillis();
        millis = millis - (millis % (1000 / 4));
        log.info("当前对齐时间戳为:" + millis);

        Assert.isTrue(rateLimiter.tryAcquire(millis));
        Assert.isTrue(rateLimiter.tryAcquire(millis + 800));
        Assert.isTrue(rateLimiter.tryAcquire(millis + 801));
        Assert.isTrue(rateLimiter.tryAcquire(millis + 802));
        Assert.isFalse(rateLimiter.tryAcquire(millis + 999));

        // 新的一秒
        Assert.isTrue(rateLimiter.tryAcquire(millis + 1000));
    }

}