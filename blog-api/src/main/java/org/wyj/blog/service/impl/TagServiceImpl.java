package org.wyj.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wyj.blog.dao.mapper.TagMapper;
import org.wyj.blog.dao.pojo.Tag;
import org.wyj.blog.service.TagService;
import org.wyj.blog.vo.HotTag;
import org.wyj.blog.vo.Result;
import org.wyj.blog.vo.TagVo;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {
        List<Tag> tags = tagMapper.findTagsByArticleId(articleId);
        return copyList(tags);
    }

    @Override
    public Result findHotTags(int limit) {
        List<HotTag> hotTags = tagMapper.findHotTags(limit);
        return Result.success(hotTags);
    }

    // 查询热门标签，被废弃的方法
//    @Override
//    public Result findHotTags(int limit) {
//        List<Long> tagIdList = tagMapper.findHotTagIdList(limit);
//        List<Tag> tagList = tagMapper.findTagsByTagIdList(tagIdList);
//        return Result.success(copyList(tagList));
//    }

    @Override
    public Result getAllTags() {
        List<Tag> tagList = tagMapper.selectList(null);
        return Result.success(copyList(tagList));
    }

    /**
     * 把Tag实例中的值赋值到TagVo实例中
     * @param tags Tag实例的集合
     * @return TagVo实例的集合
     */
    private List<TagVo> copyList(List<Tag> tags) {
        List<TagVo> tagVos = new ArrayList<>();
        for (Tag tag : tags) {
            tagVos.add(new TagVo(tag.getId().toString(), tag.getTagName()));
        }
        return tagVos;
    }
}
