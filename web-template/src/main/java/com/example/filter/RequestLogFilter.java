package com.example.filter;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

/**
 * @author 吴庆龙
 */
public class RequestLogFilter extends OncePerRequestFilter {

    private final Logger log = LoggerFactory.getLogger(RequestLogFilter.class);

    /**
     * 是否打印响应体
     */
    private boolean logRespBody = false;

    public void setLogRespBody(boolean logRespBody) {
        this.logRespBody = logRespBody;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain) throws IOException, ServletException {
        // 开始计时
        long startTs = System.currentTimeMillis();

        // 封装
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        try {
            filterChain.doFilter(requestWrapper, responseWrapper);
        } finally {
            try {
                logReqResp(startTs, requestWrapper, responseWrapper);
            } catch (Exception e) {
                // ignore
            }
        }
    }

    private void logReqResp(long startTs, ContentCachingRequestWrapper requestWrapper,
        ContentCachingResponseWrapper responseWrapper) throws IOException {
        // 结束计时，计算耗时
        long costTs = System.currentTimeMillis() - startTs;

        long logStartTs = System.currentTimeMillis();
        // uri参数
        String queryString = Optional.ofNullable(requestWrapper.getQueryString()).orElse("");
        // form参数
        StringBuilder formParam = new StringBuilder();
        // 请求体
        String requestBody = "";
        // 响应体
        String responseBody = "";

        // 请求中编码
        String characterEncoding = requestWrapper.getCharacterEncoding();

        // uri参数
        List<String> uriParamNames = Arrays.stream(queryString.split("&"))
            .map(item -> item.split("=")[0])
            .collect(Collectors.toList());

        // 解析请求参数
        String requestContentType = Optional.ofNullable(requestWrapper.getContentType()).orElse("");
        boolean isFormData = requestContentType.contains("multipart/form-data");
        if (requestContentType.contains("application/x-www-form-urlencoded") || isFormData) {
            Map<String, String[]> parameterMap = requestWrapper.getParameterMap();
            for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                String name = entry.getKey();
                String[] values = entry.getValue();
                // 过滤掉在uri中的参数
                if (uriParamNames.contains(name)) {
                    continue;
                }
                if (values == null || values.length == 0) {
                    continue;
                }
                StringBuilder valueSB = new StringBuilder();
                for (String value : values) {
                    if (valueSB.length() != 0) {
                        valueSB.append(";");
                    }
                    if (isFormData) {
                        valueSB.append(value);
                    } else {
                        valueSB.append(URLDecoder.decode(value, characterEncoding));
                    }
                }
                name = URLDecoder.decode(name, characterEncoding);
                if (formParam.length() != 0) {
                    formParam.append(",");
                }
                formParam.append("[").append(name).append("=").append(valueSB).append("]");
            }
        } else if (requestContentType.contains("application/json")) {
            byte[] contentAsByteArray = requestWrapper.getContentAsByteArray();
            // 如果Controller没有进行解析，则contentAsByteArray中没有数据
            if (contentAsByteArray.length == 0) {
                List<String> bodyContent = IOUtils.readLines(requestWrapper.getInputStream(), UTF_8);
                requestBody = String.join("", bodyContent);
            } else {
                requestBody = new String(contentAsByteArray, UTF_8);
            }
            requestBody = StringUtils.trimAllWhitespace(requestBody);
        }

        // 解析响应体
        String responseContentType = Optional.ofNullable(responseWrapper.getContentType()).orElse("");
        if (logRespBody) {
            if (responseContentType.contains("application/json") || responseContentType.contains("text/plain")) {
                byte[] byteArray = responseWrapper.getContentAsByteArray();
                responseBody = new String(byteArray, UTF_8);
                responseBody = StringUtils.trimAllWhitespace(responseBody);
            }
        }

        long logStopTs = System.currentTimeMillis();

        StringBuilder sb = new StringBuilder();
        sb.append("Method:").append(requestWrapper.getMethod()).append(" ");
        sb.append("Uri:{}").append(requestWrapper.getRequestURI()).append(" ");
        sb.append("ContentType:{}").append(requestContentType).append(" ");
        if (queryString.length() > 0) {
            sb.append("UriParam:{}").append(queryString).append(" ");
        }
        if (formParam.length() > 0) {
            sb.append("Form:{}").append(formParam).append(" ");
        }
        if (requestBody.length() > 0) {
            sb.append("Body:{}").append(requestBody).append(" ");
        }
        if (requestBody.length() > 0) {
            sb.append("Body:{}").append(requestBody).append(" ");
        }
        if (responseContentType.length() > 0) {
            sb.append("RespContentType:{}").append(responseContentType).append(" ");
        }
        if (responseBody.length() > 0) {
            sb.append("RespBody:{}").append(responseBody).append(" ");
        }
        sb.append("CostTime:{}").append(costTs).append("(").append(logStopTs - logStartTs).append(")ms");
        log.info(sb.toString());

        // 写入响应消息到客户端
        responseWrapper.copyBodyToResponse();
    }

}
