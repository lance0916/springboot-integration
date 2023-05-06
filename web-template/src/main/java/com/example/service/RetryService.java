package com.example.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

/**
 * @author WuQinglong
 * @since 2023/1/30 14:28
 */
@Slf4j
@Service
public class RetryService {

    @Retryable(backoff = @Backoff(delay = 2000, multiplier = 1.5), exclude = IllegalArgumentException.class)
    public void testRetry() {
        log.info("执行逻辑");
        throw new RuntimeException();
    }

}
