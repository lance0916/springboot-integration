package com.example.sentinel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author WuQinglong
 */
@RestController
@RequestMapping("/web")
public class IndexController {

    private final Logger log = LoggerFactory.getLogger(IndexController.class);

    @Resource
    private IndexService indexService;

    @GetMapping("/index")
    public String index() {
        indexService.username();
        return "Hello";
    }

}
