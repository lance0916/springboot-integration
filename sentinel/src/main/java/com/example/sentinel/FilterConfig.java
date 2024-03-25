package com.example.sentinel;

import com.alibaba.csp.sentinel.adapter.servlet.CommonFilter;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.alibaba.csp.sentinel.adapter.servlet.CommonFilter.HTTP_METHOD_SPECIFY;

/**
 * @author WuQinglong
 */
//@Configuration
public class FilterConfig implements InitializingBean {

    @Bean
    public FilterRegistrationBean<Filter> sentinelFilterRegistration() {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new CommonFilter());
        registration.addUrlPatterns("/*");
        registration.setName("sentinelFilter");
        registration.setOrder(1);

        Map<String, String> map = new HashMap<>();
        map.put(HTTP_METHOD_SPECIFY, "true");
        registration.setInitParameters(map);
        return registration;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 定义规则，可以使用 控制台 页面动态进行配置
        List<FlowRule> rules = new ArrayList<>();

        // 流量控制规则 https://sentinelguard.io/zh-cn/docs/flow-control.html
        FlowRule rule = new FlowRule();
        rule.setResource("GET:/web/index");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setCount(1);
        rules.add(rule);

//        FlowRuleManager.loadRules(rules);
    }

}