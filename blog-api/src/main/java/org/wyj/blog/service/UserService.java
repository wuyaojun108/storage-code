package org.wyj.blog.service;

import org.wyj.blog.vo.RegisterUserVo;
import org.wyj.blog.vo.Result;
import org.wyj.blog.vo.params.LoginParams;

import java.io.IOException;

public interface UserService {

    /**
     * 处理登录逻辑
     * @param loginParams 登录时用户需要提供的参数
     * @return 返回登录结果
     */
    Result login(LoginParams loginParams);

    Result findUserByToken(String token);

    Result logout(String token);

    Result register(RegisterUserVo registerUserVo);
}
