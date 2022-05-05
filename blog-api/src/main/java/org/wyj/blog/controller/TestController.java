package org.wyj.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.wyj.blog.utils.UserThreadLocal;
import org.wyj.blog.vo.Result;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    public Result test() {
        System.out.println("UserThreadLocal.get() = " + UserThreadLocal.get());
        return Result.success(null);
    }

}
