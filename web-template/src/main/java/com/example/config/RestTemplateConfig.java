package com.example.config;

import cn.hutool.core.date.DatePattern;
import com.example.bean.constants.HttpConstant;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jetbrains.annotations.NotNull;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.lang.NonNull;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Arrays;

/**
 * @author WuQinglong
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        // 自定义 jackson
        ObjectMapper objectMapper = new ObjectMapper()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .setDateFormat(new SimpleDateFormat(DatePattern.NORM_DATETIME_PATTERN));

        RestTemplate restTemplate = new RestTemplate(customHttpRequestFactory());
        restTemplate.setMessageConverters(Arrays.asList(
                new ByteArrayHttpMessageConverter(),
                new StringHttpMessageConverter(StandardCharsets.UTF_8),
                new AllEncompassingFormHttpMessageConverter(),
                new MappingJackson2HttpMessageConverter(objectMapper)
        ));
        restTemplate.setInterceptors(Arrays.asList(
            new ClientHttpRequestInterceptor() {
                @NotNull
                @Override
                public ClientHttpResponse intercept(@NonNull HttpRequest request,
                    @NonNull byte[] body, @NonNull ClientHttpRequestExecution execution)
                    throws IOException {

                    String traceId = MDC.get(HttpConstant.TRACE_ID);
                    if (traceId == null || traceId.isEmpty()) {
                        traceId = System.currentTimeMillis() + "";
                    }
                    String spanId = System.currentTimeMillis() + "";

                    request.getHeaders().add(HttpConstant.TRACE_ID, traceId);

                    return execution.execute(request, body);
                }
            }
        ));
        return restTemplate;
    }

    public HttpComponentsClientHttpRequestFactory customHttpRequestFactory() {
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                // 从连接池中获取链接的超时时间
                .setConnectionRequestTimeout(1000 * 3)
                // 建立链接的超时时间
                .setConnectTimeout(1000 * 3)
                // 接收两个数据包的间隔时间，也可以理解为读取服务器数据的超时时间
                .setSocketTimeout(1000 * 10)
                // 不允许重定向
                .setRedirectsEnabled(false)
                .build();

        HttpClient httpClient = HttpClientBuilder.create()
                .setMaxConnTotal(500)
                .setMaxConnPerRoute(300)
                .setDefaultRequestConfig(defaultRequestConfig)
                // 自定义 socket 配置
                .setDefaultConnectionConfig(
                        ConnectionConfig.custom()
                                .setCharset(StandardCharsets.UTF_8)
                                .build()
                )
                // 设置连接存活时间，并开启自动关闭
//                .setConnectionTimeToLive(5, TimeUnit.SECONDS)
//                .evictExpiredConnections()
//                .evictIdleConnections(10, TimeUnit.SECONDS)
                // 禁用 cookie
                .disableCookieManagement()
                .build();
        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }

}
