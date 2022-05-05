package org.wyj.blog.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.wyj.blog.BaseTest;
import org.wyj.blog.dao.mapper.ArticleMapper;
import org.wyj.blog.dao.pojo.Article;
import org.wyj.blog.vo.dos.Archive;

import java.util.List;

public class ArticleMapperTest extends BaseTest {

    // articleMapper测试
    @Autowired
    private ArticleMapper articleMapper;

    @Test
    public void articleMapperTest() {
        List<Article> articles = articleMapper.selectList(null);
        articles.forEach(System.out::println);
    }

    @Test
    public void listArchiveTest(){
        List<Archive> archives = articleMapper.listArchive(5);
        archives.forEach(System.out::println);
    }

    @Test
    public void selectCountTest(){
        Integer integer = articleMapper.selectCount(null);
        System.out.println("integer = " + integer);
    }



}
