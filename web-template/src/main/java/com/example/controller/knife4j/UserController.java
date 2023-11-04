package com.example.controller.knife4j;

import java.util.List;

import com.example.aspect.annotation.SwaggerUserTag;
import com.example.bean.resp.BaseResp;
import com.example.bean.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WuQinglong
 * @since 2023/5/12 15:48
 */
@SwaggerUserTag
@Api(tags = "用户模块")
@RestController
@RequestMapping("/knife4j/user")
public class UserController {

    @ApiImplicitParams({
        @ApiImplicitParam(name = "pageNum", value = "当前页码", required = true),
        @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true),
    })
    @ApiOperation(value = "获取用户列表")
    @GetMapping("/list")
    public BaseResp<List<UserVO>> list(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return new BaseResp<>();
    }

}
