package org.wyj.blog.service;

import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.wyj.blog.BaseTest;
import org.wyj.blog.vo.ArticleMessage;
import org.wyj.blog.vo.Result;
import org.wyj.blog.vo.params.PageParams;

import java.util.List;

public class ArticleServiceTest extends BaseTest {

    @Autowired
    private ArticleService articleService;

    @Test
    public void serviceTest(){
        Result result = articleService.listArticle(new PageParams(1, 10));
        List<Object> data = (List<Object>)result.getData();
        data.forEach(System.out::println);
    }

    @Test
    public void hotArticlesTest(){
        Result result = articleService.hotArticles(5);
        System.out.println("result = " + result);
    }

    @Test
    public void newArticlesTest(){
        Result result = articleService.newArticles(5);
        System.out.println("result = " + result);
    }

    @Test
    public void listArchiveTest(){
        Result result = articleService.listArchive(5);
        System.out.println("result = " + result);
    }

    @Test
    public void findArticleByIdTest(){
        Result result = articleService.findArticleById(1L);
        System.out.println("result = " + result);
    }

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Test
    public void rocketMqTest(){
        System.out.println("rocketMQTemplate = " + rocketMQTemplate);
        ArticleMessage articleMessage = new ArticleMessage();
        articleMessage.setOffset(4);
        rocketMQTemplate.convertAndSend("spring-boot-test1", articleMessage);
    }

    @Test
    public void rocketMqTest2(){

    }
}
