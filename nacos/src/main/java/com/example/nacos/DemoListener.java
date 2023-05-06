package com.example.nacos;

import cn.hutool.core.thread.ThreadUtil;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author WuQinglong
 */
@Component
public class DemoListener implements ApplicationRunner {

    @NacosValue(value = "${example.name}", autoRefreshed = true)
    private String name;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        while (true) {
            System.out.println("Nacos Config:" + name);
            ThreadUtil.sleep(1000);
        }
    }

}
