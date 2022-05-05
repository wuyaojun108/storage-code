package org.wyj.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentReplyVo {
    private String id;
    private String content;
    private String createDate;
    private String commentId;
    private UserVo commentReplyAuthor;
}
