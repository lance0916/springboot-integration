package com.example.controller;

import com.example.aspect.annotation.NoValid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WuQinglong
 */
@RestController
@RequestMapping("/healthCheck")
@NoValid
public class HealthCheckController {
    private final Logger logger = LoggerFactory.getLogger(HealthCheckController.class);

    /**
     * 更改状态需要的密钥
     */
    private static final String SECURE_KEY = "09704be6-11a4-4b24-b421-aa2e1b8855b7";

    /**
     * 默认的 Http 状态
     */
    private volatile int currentSystemStatus = HttpStatus.OK.value();

    /**
     * 心跳检测
     */
    @GetMapping("/status")
    public ResponseEntity<Void> getStatus() {
        logger.info("心跳检测状态码:{}", currentSystemStatus);
        return ResponseEntity.status(currentSystemStatus).build();
    }

    /**
     * 更新状态码
     * 09704be6-11a4-4b24-b421-aa2e1b8855b7
     */
    @PutMapping(path = "/status", consumes = {MediaType.APPLICATION_JSON_VALUE},
        produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateStatus(@Validated @RequestBody Status status) {
        // 校验 secureKey
        if (!SECURE_KEY.equals(status.getSecureKey())) {
            return "fail";
        }
        if (isValidHttpStatus(status.getStatus())) {
            currentSystemStatus = status.getStatus();
            logger.info("更新状态码成功: {}", currentSystemStatus);
            return "success";
        }
        return "fail";
    }

    private boolean isValidHttpStatus(int httpStatus) {
        return HttpStatus.resolve(httpStatus) != null;
    }

    public static class Status {
        private String secureKey;
        private Integer status;

        public String getSecureKey() {
            return secureKey;
        }

        public void setSecureKey(String secureKey) {
            this.secureKey = secureKey;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }
    }

}
