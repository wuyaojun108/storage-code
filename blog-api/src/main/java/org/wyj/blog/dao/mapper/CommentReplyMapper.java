package org.wyj.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.wyj.blog.dao.pojo.CommentReply;

import java.util.List;

public interface CommentReplyMapper extends BaseMapper<CommentReply> {
    @Select("SELECT max(id) FROM ms_comment_reply")
    Long getMaxId();

    @Select("SELECT id, content, create_date AS createDate " +
            "    , comment_id AS commentId" +
            "    , comment_reply_author_id AS commentReplyAuthorId " +
            "FROM ms_comment_reply " +
            "WHERE comment_id = #{commentId}")
    List<CommentReply> commentReplyListByCommentId(Long commentId);
}
