package org.wyj.blog.dao.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentReply {
    private Long id;
    private String content;
    private Long createDate;
    private Long commentId;
    private Long commentReplyAuthorId;
}
