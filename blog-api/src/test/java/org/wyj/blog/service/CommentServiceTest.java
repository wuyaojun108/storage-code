package org.wyj.blog.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.wyj.blog.BaseTest;
import org.wyj.blog.vo.Result;

public class CommentServiceTest extends BaseTest {

    @Autowired
    private CommentService commentService;

    @Test
    public void commentListTest(){
        Result result = commentService.commentList(1L);
        System.out.println("result = " + result);
    }
}
