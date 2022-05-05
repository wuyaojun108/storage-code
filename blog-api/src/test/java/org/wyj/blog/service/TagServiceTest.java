package org.wyj.blog.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.wyj.blog.BaseTest;
import org.wyj.blog.vo.Result;
import org.wyj.blog.vo.TagVo;

import java.util.List;

public class TagServiceTest extends BaseTest {

    @Autowired
    private TagService tagService;

    @Test
    public void findTagsByArticleIdTest(){
        List<TagVo> tagVoList = tagService.findTagsByArticleId(1L);
        tagVoList.forEach(System.out::println);
    }

    @Test
    public void findHotTagsTest(){
        Result hotTags = tagService.findHotTags(5);
        System.out.println("hotTags = " + hotTags);
    }
}
