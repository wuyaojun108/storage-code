package org.wyj.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.wyj.blog.dao.pojo.Comment;

import java.util.List;

public interface CommentMapper extends BaseMapper<Comment> {

    @Select("SELECT id, content, create_date AS createDate, article_id AS articleId " +
            "    , comment_author_id commentAuthorId " +
            "FROM ms_comment " +
            "WHERE article_id = #{articleId} " +
            "ORDER BY create_date DESC")
    List<Comment> getCommentByArticleId(Long articleId);

    @Select("SELECT max(id) FROM ms_comment")
    Long getMaxId();
}
