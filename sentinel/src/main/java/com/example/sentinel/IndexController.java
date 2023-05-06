package com.example.sentinel;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WuQinglong
 */
@RestController
@RequestMapping("/web")
public class IndexController {

    @GetMapping("/index")
    public String index() {
        return "Web01 Success.";
    }

}
