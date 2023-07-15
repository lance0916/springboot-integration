package com.example.config;

import com.example.filter.RequestLogFilter;
import com.example.filter.TraceFilter;
import javax.servlet.DispatcherType;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author WuQinglong
 * @since 2023/7/15 09:42
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean<TraceFilter> traceFilterBean() {
        TraceFilter traceFilter = new TraceFilter();

        FilterRegistrationBean<TraceFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(traceFilter);
        bean.setDispatcherTypes(DispatcherType.REQUEST);
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        bean.addUrlPatterns("/*");
        return bean;
    }

    @Bean
    public FilterRegistrationBean<RequestLogFilter> requestLogFilterBean() {
        RequestLogFilter requestLogFilter = new RequestLogFilter();

        FilterRegistrationBean<RequestLogFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(requestLogFilter);
        bean.setDispatcherTypes(DispatcherType.REQUEST);
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
        bean.addUrlPatterns("/*");
        return bean;
    }

}
