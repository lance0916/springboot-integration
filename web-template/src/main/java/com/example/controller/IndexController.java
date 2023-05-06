package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
