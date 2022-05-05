package org.wyj.blog.service.impl;

import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wyj.blog.dao.mapper.CommentMapper;
import org.wyj.blog.dao.mapper.CommentReplyMapper;
import org.wyj.blog.dao.pojo.Comment;
import org.wyj.blog.dao.pojo.CommentReply;
import org.wyj.blog.dao.pojo.SysUser;
import org.wyj.blog.utils.UserThreadLocal;
import org.wyj.blog.vo.*;
import org.wyj.blog.service.CommentService;
import org.wyj.blog.service.SysUserService;
import org.wyj.blog.vo.params.CommentParam;
import org.wyj.blog.vo.params.CommentReplyParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentReplyMapper commentReplyMapper;

    @Autowired
    private SysUserService sysUserService;

    @Override
    public Result commentList(Long articleId) {
        List<Comment> comments = commentMapper.getCommentByArticleId(articleId);
        List<CommentVo> commentVoList = copyList(comments);
        return Result.success(commentVoList);
    }

    @Override
    @Transactional
    public Result createComment(CommentParam commentParam) {
        Comment comment = new Comment();
        comment.setId(commentMapper.getMaxId() + 1);
        comment.setArticleId(commentParam.getArticleId());
        comment.setCommentAuthorId(commentParam.getAuthorId());
        comment.setContent(commentParam.getContent());
        comment.setCreateDate(System.currentTimeMillis());
        commentMapper.insert(comment);
        return Result.success(null);
    }

    @Override
    @Transactional
    public Result createReply(CommentReplyParam commentReplyParam) {
        String commentId = commentReplyParam.getCommentId();
        String content = commentReplyParam.getContent();
        String commentReplyAuthorId = commentReplyParam.getCommentReplyAuthorId();
        Long id = commentReplyMapper.getMaxId();
        CommentReply commentReply = new CommentReply(id + 1, content
                , System.currentTimeMillis()
                , Long.parseLong(commentId)
                , Long.parseLong(commentReplyAuthorId));
        commentReplyMapper.insert(commentReply);
        return Result.success(null);
    }

    @Override
    public Result commentReplyListByCommentId(Long commentId) {
        List<CommentReply> commentReplyList
                = commentReplyMapper.commentReplyListByCommentId(commentId);
        ArrayList<CommentReplyVo> commentRelyVoList = new ArrayList<>();
        for (CommentReply commentReply : commentReplyList) {
            commentRelyVoList.add(copy(commentReply));
        }
        return Result.success(commentRelyVoList);
    }

    private List<CommentVo> copyList(List<Comment> comments) {
        ArrayList<CommentVo> commentVoList = new ArrayList<>();
        for (Comment comment : comments) {
            commentVoList.add(copy(comment));
        }
        return commentVoList;
    }

    private CommentReplyVo copy(CommentReply commentReply){
        CommentReplyVo commentReplyVo = new CommentReplyVo();

        commentReplyVo.setId(Long.toString(commentReply.getId()));
        commentReplyVo.setContent(commentReply.getContent());
        commentReplyVo.setCommentId(Long.toString(commentReply.getCommentId()));
        commentReplyVo.setCreateDate(new DateTime(commentReply.getCreateDate())
                .toString("yyyy-MM-dd HH:mm"));
        Long authorId = commentReply.getCommentReplyAuthorId();
        SysUser sysUser = sysUserService.findUserById(authorId);
        commentReplyVo.setCommentReplyAuthor(new UserVo(sysUser.getNickname()
                , sysUser.getAvatar(), sysUser.getId()));

        return commentReplyVo;
    }

    private CommentVo copy(Comment comment) {
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment, commentVo);
        commentVo.setId(Long.toString(comment.getId()));
        commentVo.setCreateDate(new DateTime(comment.getCreateDate())
                .toString("yyyy-MM-dd HH:mm"));
        Long authorId = comment.getCommentAuthorId();
        SysUser sysUser = sysUserService.findUserById(authorId);
        commentVo.setCommentAuthor(new UserVo(sysUser.getNickname()
                , sysUser.getAvatar(), sysUser.getId()));
        return commentVo;
    }

}
