package com.afeibaili.chat.consts;

/**
 * @author AfeiB
 * @description 返回代码枚举类
 * @createDate 2024/8/16 下午1:36
 */

public enum ResultConst {
    SUCCESS(200, "成功!"),
    USERNAME_EXISTS(506, "用户已存在!"),
    USERNAME_ERROR(507, "用户不存在!"),
    PARAMETER_ERROR(508, "密码不正确!"),
    USER_UPDATE_ERROR(509, "用户更新失败!");

    private Integer code;
    private String message;

    ResultConst(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
