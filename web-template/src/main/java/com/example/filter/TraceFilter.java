package com.example.filter;

import com.example.bean.constants.HttpConstant;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
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
    protected void doFilterInternal(@NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
        throws ServletException, IOException {

        // traceId，上游没有传就生成一个
        String traceId = request.getHeader(HttpConstant.TRACE_ID);
        if (traceId == null || traceId.isEmpty()) {
            traceId = System.currentTimeMillis() + "";
        }
        MDC.put(HttpConstant.TRACE_ID, traceId);

        // 上游没有传 spanId 就生成一个
        String spanId = request.getHeader(HttpConstant.SPAN_ID);
        if (spanId == null || spanId.isEmpty()) {
            spanId = System.currentTimeMillis() + "";
        }
        MDC.put(HttpConstant.SPAN_ID, traceId);
    }
}
