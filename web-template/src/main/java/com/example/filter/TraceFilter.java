package com.example.filter;

import com.example.bean.constants.TraceConstant;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author WuQinglong
 * @since 2023/2/18 08:23
 */
public class TraceFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain) {

        // traceId，上游没有传就生成一个
        String traceId = request.getHeader(TraceConstant.TRACE_ID);
        traceId = traceId == null ? "" : traceId;
        MDC.put(TraceConstant.TRACE_ID, traceId);

        // 获取 spanid，记录为 pspanid
        String pspanId = request.getHeader(TraceConstant.SPAN_ID);
        pspanId = pspanId == null ? "" : pspanId;
        MDC.put(TraceConstant.PSPAN_ID, pspanId);

        // 生成请求在本系统内的唯一 id，spanid
        String spanid = String.valueOf(System.currentTimeMillis());
        MDC.put(TraceConstant.SPAN_ID, spanid);
    }
}
