package com.snailwu.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
