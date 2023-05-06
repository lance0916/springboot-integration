package com.example.bean.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WuQinglong
 * @since 2023/1/6 11:34
 */
@Getter
@Setter
public class BaseResp<T> {

    @ApiModelProperty(value = "响应码")
    private int code;

    @ApiModelProperty(value = "响应信息")
    private String msg;

    @ApiModelProperty(value = "响应数据")
    private T data;

    public BaseResp() {
    }

    public BaseResp(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BaseResp(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

}
