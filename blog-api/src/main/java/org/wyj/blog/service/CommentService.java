package org.wyj.blog.service;

import org.wyj.blog.vo.Result;
import org.wyj.blog.vo.params.CommentParam;
import org.wyj.blog.vo.params.CommentReplyParam;

public interface CommentService {
    /**
     * 获取某篇文章的评论列表
     * @param articleId 文章ID
     * @return 评论列表
     */
    Result commentList(Long articleId);

    /**
     * 提交评论
     * @param commentParam 评论信息
     * @return 提交结果
     */
    Result createComment(CommentParam commentParam);

    /**
     * 回复某条评论
     */
    Result createReply(CommentReplyParam commentReplyParam);

    Result commentReplyListByCommentId(Long commentId);
}
