package com.example.sentinel;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.circuitbreaker.CircuitBreaker;
import com.alibaba.csp.sentinel.slots.block.degrade.circuitbreaker.CircuitBreakerStateChangeObserver;
import com.alibaba.csp.sentinel.slots.block.degrade.circuitbreaker.CircuitBreakerStrategy;
import com.alibaba.csp.sentinel.slots.block.degrade.circuitbreaker.EventObserverRegistry;
import com.alibaba.csp.sentinel.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SentinelApplication implements InitializingBean, ApplicationRunner {

    private final Logger log = LoggerFactory.getLogger(SentinelApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SentinelApplication.class, args);
    }

    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }

    @Resource
    private IndexService indexService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
    }

    private static void initDegradeRule() {
        DegradeRule rule = new DegradeRule("username")
            .setGrade(CircuitBreakerStrategy.ERROR_RATIO.getType())
            .setCount(0.2)
            .setMinRequestAmount(5)
            .setStatIntervalMs(10_000)
            .setTimeWindow(10);

        List<DegradeRule> rules = new ArrayList<>();
        rules.add(rule);
        System.out.println("Degrade rule loaded: " + rules);
        DegradeRuleManager.loadRules(rules);

        EventObserverRegistry.getInstance().addStateChangeObserver("username", new CircuitBreakerStateChangeObserver() {
            @Override
            public void onStateChange(CircuitBreaker.State prevState, CircuitBreaker.State newState, DegradeRule rule,
                Double snapshotValue) {
                if (newState == CircuitBreaker.State.OPEN) {
                    // 变换至 OPEN state 时会携带触发时的值
                    System.out.println(String.format("%s -> OPEN at %d, snapshotValue=%.2f", prevState.name(),
                        TimeUtil.currentTimeMillis(), snapshotValue));
                } else {
                    System.out.println(String.format("%s -> %s at %d", prevState.name(), newState.name(),
                        TimeUtil.currentTimeMillis()));
                }
            }
        });
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initDegradeRule();
    }

}
