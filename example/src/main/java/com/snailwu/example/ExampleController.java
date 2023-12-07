package com.snailwu.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author WuQinglong
 * @since 2023/3/14 20:19
 */
@RestController
public class ExampleController {

    @GetMapping("/index")
    public String index() {
        return "Hello Index";
    }

    @PostMapping("/bodyTest")
    public String bodyTest(@RequestBody Map<String, Object> aMap, @RequestBody Map<String, Object> bMap) {
        System.out.println(aMap);
        System.out.println(bMap);
        return "success";
    }

}
