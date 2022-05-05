package org.wyj.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wyj.blog.dao.pojo.CommentReply;
import org.wyj.blog.dao.pojo.CommentReply;
import org.wyj.blog.log.LogAnnotation;
import org.wyj.blog.service.CommentService;
import org.wyj.blog.vo.Result;
import org.wyj.blog.vo.params.CommentParam;
import org.wyj.blog.vo.params.CommentReplyParam;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 获取某篇文章的评论列表
     * @param articleId 文章ID
     * @return 评论列表
     */
    @LogAnnotation(module = "评论", operation = "获取某篇文章的评论")
    @PostMapping("/article/{id}")
    public Result commentList(@PathVariable("id") Long articleId) {
        return commentService.commentList(articleId);
    }

    @LogAnnotation(module = "评论", operation = "发表某篇文章的评论")
    @PostMapping("/create")
    public Result createComment(@RequestBody CommentParam commentParam) {
        return commentService.createComment(commentParam);
    }

    @PostMapping("/createReply")
    public Result createReply(@RequestBody CommentReplyParam commentReplyParam){
        return commentService.createReply(commentReplyParam);
    }

    @GetMapping("/commentReplyListByCommentId")
    public Result commentReplyListByCommentId(String commentId){
        return commentService.commentReplyListByCommentId(Long.parseLong(commentId));
    }
}
