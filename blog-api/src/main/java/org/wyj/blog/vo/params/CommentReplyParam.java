package org.wyj.blog.vo.params;

import lombok.Data;

@Data
public class CommentReplyParam {
    private String commentId;
    private String content;
    private String commentReplyAuthorId;
}
