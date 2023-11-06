package com.example.controller.knife4j;

import com.example.aspect.annotation.SwaggerCommonTag;
import com.example.bean.req.UserEditReq;
import com.example.bean.resp.BaseResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WuQinglong
 * @since 2023/2/13 17:57
 */
@Slf4j
@SwaggerCommonTag
@Api(tags = "首页模块")
@RestController
@RequestMapping("/knife4j")
public class Knife4jController {

    // CHECKSTYLE:OFF
    @ApiImplicitParam(name = "name", value = "姓名", required = true)
    @ApiOperation(value = "向客人问好")
    @GetMapping("/sayHi") public ResponseEntity<String>
    sayHi(@RequestParam(value = "name") String name) {
    // CHECKSTYLE:ON
        int a = 0; // SUPPRESS CHECKSTYLE 121323你好

        return ResponseEntity.ok("Hi:" + name);
    }

    @ApiOperation(value = "保存实体")
    @PostMapping("/saveEntity")
    public BaseResp<String> saveEntity(@RequestBody UserEditReq req) {
        return new BaseResp<>();
    }

}
