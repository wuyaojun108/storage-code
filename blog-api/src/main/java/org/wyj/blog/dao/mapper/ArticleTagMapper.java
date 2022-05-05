package org.wyj.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.wyj.blog.dao.pojo.ArticleTag;

public interface ArticleTagMapper extends BaseMapper<ArticleTag> {
    @Select("SELECT max(id) FROM ms_article_tag")
    Long getMaxId();
}
