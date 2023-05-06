package com.example.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WuQinglong
 * @since 2023/1/6 11:30
 */
@Getter
@Setter
@ApiModel(description = "用户实体")
public class UserVO {

    @ApiModelProperty(value = "姓名")
    private String name;

}
