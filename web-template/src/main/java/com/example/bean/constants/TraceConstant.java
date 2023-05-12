package com.example.bean.constants;

/**
 * @author WuQinglong
 * @since 2023/2/18 09:24
 */
public interface TraceConstant {

    /**
     * 整个请求链路中的唯一id
     */
    String TRACE_ID = "traceid";

    /**
     * 上游系统传过来的 spanid，进入本系统后会被记录成 parent spanid，简称 pspanid
     */
    String PSPAN_ID = "pspanid";

    /**
     * 请求进入本系统后，生成请求在本系统内的唯一id
     */
    String SPAN_ID = "spanId";

}
