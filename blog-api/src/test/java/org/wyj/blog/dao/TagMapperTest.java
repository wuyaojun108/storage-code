package org.wyj.blog.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.wyj.blog.BaseTest;
import org.wyj.blog.dao.mapper.TagMapper;
import org.wyj.blog.dao.pojo.Tag;

import java.util.ArrayList;
import java.util.List;

public class TagMapperTest extends BaseTest {
    // TagMapper测试
    @Autowired
    private TagMapper tagMapper;

    @Test
    public void tagMapperTest() {
        List<Tag> tags = tagMapper.selectList(null);
        tags.forEach(System.out::println);
    }

    @Test
    public void findTagsByArticleIdTest() {
        List<Tag> tags = tagMapper.findTagsByArticleId(1L);
        tags.forEach(System.out::println);
    }

    @Test
    public void findHotTagIdListTest(){
        List<Long> hotTagIdList = tagMapper.findHotTagIdList(5);
        hotTagIdList.forEach(System.out::println);
    }

    @Test
    public void findTagsByTagIdListTest(){
        ArrayList<Long> longs = new ArrayList<>();
        longs.add(1L);
        longs.add(2L);
        longs.add(3L);
        longs.add(4L);
        System.out.println("tagMapper = " + tagMapper);
        System.out.println("longs = " + longs);
        List<Tag> tagsByTagIdList = tagMapper.findTagsByTagIdList(longs);
        tagsByTagIdList.forEach(System.out::println);
    }



}
