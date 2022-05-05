package org.wyj.blog.utils;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.wyj.blog.BaseTest;

public class RedisTemplateTest extends BaseTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void test(){
        System.out.println("redisTemplate = " + redisTemplate);
        redisTemplate.opsForValue().set("token", "15f08f86435b060236fa9ccea751e9e5");
    }
}
