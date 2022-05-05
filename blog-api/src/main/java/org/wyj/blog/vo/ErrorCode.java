package org.wyj.blog.vo;

/**
 * 定义了错误编码和错误信息
 */
public enum ErrorCode {
    PARAMS_ERROR(10001, "参数有误"),
    ACCOUNT_PWD_NOT_EXISTS(10002, "用户名或密码不存在"),
    TOKEN_ERROR(10003, "token不正确"),
    ACCOUNT_EXISTS(10004, "账户已存在，无法注册"),
    NO_PERMISSION(70001, "无权限访问"),
    SESSION_TIMEOUT(90001, "会话超时"),
    NO_LOGIN(90002, "未登录");


    private int code;
    private String msg;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
