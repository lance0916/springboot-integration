package com.example.filter;

import com.example.bean.constants.TraceConstant;
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
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain) throws ServletException, IOException {

        // traceid，上游没有传就生成一个
        String traceid = request.getHeader(TraceConstant.TRACE_ID);
        traceid = traceid == null ? "" : traceid;
        MDC.put(TraceConstant.TRACE_ID, traceid);

        // 获取 spanid，记录为 pspanid
        String pspanid = request.getHeader(TraceConstant.SPAN_ID);
        pspanid = pspanid == null ? "" : pspanid;
        MDC.put(TraceConstant.PSPAN_ID, pspanid);

        // 生成请求在本系统内的唯一 id，spanid
        String spanid = String.valueOf(System.currentTimeMillis());
        MDC.put(TraceConstant.SPAN_ID, spanid);

        try {
            filterChain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }
}
