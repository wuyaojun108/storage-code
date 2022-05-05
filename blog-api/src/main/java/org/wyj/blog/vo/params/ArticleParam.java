package org.wyj.blog.vo.params;

import lombok.Data;

import java.util.List;

@Data
public class ArticleParam {
    private String authorId;

    private String title;

    private String summary;

    private String categoryName;

    private List<String> tagName;

    private String content;

    private String contentHtml;
}
