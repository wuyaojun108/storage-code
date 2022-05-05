package org.wyj.blog.vo.params;

import lombok.Data;

@Data
public class CommentParam {
    private Long articleId;
    private Long authorId;
    private String content;
}
