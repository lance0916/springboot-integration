package com.example.sentinel;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author WuQinglong
 * @date 2024/1/10 16:38
 */
@Service
public class IndexService {

    private final Logger log = LoggerFactory.getLogger(IndexService.class);

    @SentinelResource(value = "username", fallback = "usernameFallback")
    public void username() {
        int i = 1 / 0;
        log.info("username");
    }

    public void usernameFallback() {
        log.info("usernameFallback");
    }

}
