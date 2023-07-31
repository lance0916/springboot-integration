package com.example.util;

import cn.hutool.core.lang.UUID;

/**
 * @author WuQinglong
 * @since 2023/5/30 10:43
 */
public class TraceUtil {

    /**
     * 生成TraceId
     */
    public static String generateTraceId() {
        return UUID.fastUUID().toString(true);
    }

    /**
     * 生成 SpanId
     */
    public static String generateSpanId() {
        return UUID.fastUUID().toString(true);
    }

}
