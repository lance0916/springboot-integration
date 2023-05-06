package com.example.bean.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WuQinglong
 * @since 2023/1/7 08:33
 */
@Getter
@Setter
public class UserEditReq {

    @ApiModelProperty("用户Id")
    private Integer id;

    @ApiModelProperty("用户名")
    private String name;

}
