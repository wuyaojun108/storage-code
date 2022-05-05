package org.wyj.blog.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wyj.blog.vo.Result;

@ControllerAdvice
public class AllExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    public Result doException(Exception exception) {
        exception.printStackTrace();
        return Result.fail(-999, "系统异常");
    }
}
