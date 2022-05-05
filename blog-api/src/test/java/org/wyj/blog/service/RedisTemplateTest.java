package org.wyj.blog.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.wyj.blog.BaseTest;

import java.util.Set;

public class RedisTemplateTest extends BaseTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void test1(){
        System.out.println("redisTemplate = " + redisTemplate);
        int offset = 4;
        String pattern =
                "*ArticleController::listArticles::[PageParams(offset=" + offset +"*";
        pattern = "*listArticles::\\[PageParams\\(offset=" + offset + "*";
        System.out.println("pattern = " + pattern);
        Set<String> keys = redisTemplate.keys(pattern);
        if(keys != null){
            keys.forEach(System.out::println);
        }
    }
}
