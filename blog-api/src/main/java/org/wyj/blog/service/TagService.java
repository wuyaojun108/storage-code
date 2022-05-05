package org.wyj.blog.service;

import org.wyj.blog.vo.Result;
import org.wyj.blog.vo.TagVo;

import java.util.List;

public interface TagService {
    List<TagVo> findTagsByArticleId(Long articleId);

    /**
     * 查询热门标签
     * @param limit 限制查询出来的个数
     */
    Result findHotTags(int limit);

    /**
     * 获取所有标签
     */
    Result getAllTags();

}
