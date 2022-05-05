package org.wyj.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wyj.blog.log.LogAnnotation;
import org.wyj.blog.service.UserService;
import org.wyj.blog.vo.RegisterUserVo;
import org.wyj.blog.vo.Result;
import org.wyj.blog.vo.params.LoginParams;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    /**
     * 处理登录逻辑
     */
    @LogAnnotation(module = "用户", operation = "登录")
    @PostMapping("/login")
    public Result login(@RequestBody LoginParams loginParams) {
        return userService.login(loginParams);
    }

    /**
     * 获取当前用户
     */
    @LogAnnotation(module = "用户", operation = "获取当前用户")
    @PostMapping("/currentUser")
    public Result currentUser(@RequestHeader("authorization") String token) {
        return userService.findUserByToken(token);
    }

    /**
     * 处理登出逻辑
     */
    @LogAnnotation(module = "用户", operation = "登出")
    @PostMapping("/logout")
    public Result logout(@RequestHeader("authorization") String token) {
        return userService.logout(token);
    }

    /**
     * 处理注册逻辑
     */
    @LogAnnotation(module = "用户", operation = "注册")
    @PostMapping("/register")
    public Result register(@RequestBody RegisterUserVo registerUserVo){
        return userService.register(registerUserVo);
    }
}
