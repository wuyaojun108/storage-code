package org.wyj.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 响应码，例如200
     */
    private int code;

    /**
     * 消息
     */
    private String msg;

    /**
     * 响应数据
     */
    private Object data;


    /**
     * 响应成功调用的方法
     */
    public static Result success(Object data) {
        return new Result(true, 200, "success", data);
    }

    /**
     * 响应失败调用的方法
     */
    public static Result fail(int code, String msg) {
        return new Result(false, code, msg, null);
    }
}
