package org.wyj.blog.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.wyj.blog.dao.pojo.ArticleBody;

public interface ArticleBodyMapper extends BaseMapper<ArticleBody> {
    @Select("SELECT max(id) FROM ms_article_body")
    Long getMaxId();


}
