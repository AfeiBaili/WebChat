package com.afeibaili.chat.pojo;

import com.afeibaili.chat.consts.ResultConst;
import lombok.Data;

/**
 * @author AfeiB
 * @description 返回结果类
 * @createDate 2024/8/16 下午1:35
 */
@Data
public class Result {
    private String message;

    private Integer code;

    private Object data;

    public Result(String data, String message, Integer code) {
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public Result(Object data, ResultConst resultCodeMessage) {
        this.data = data;
        this.message = resultCodeMessage.getMessage();
        this.code = resultCodeMessage.getCode();
    }
}
