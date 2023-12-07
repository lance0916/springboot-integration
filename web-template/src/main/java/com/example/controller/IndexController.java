package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

/**
 * @author WuQinglong
 * @since 2022/11/28 6:05 PM
 */
@RestController
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "success";
    }

    @PostMapping("/bodyTest")
    public String bodyTest(@RequestBody Map<String, Object> aMap, @RequestBody Map<String, Object> bMap) {
        System.out.println(aMap);
        System.out.println(bMap);
        return "success";
    }

}
