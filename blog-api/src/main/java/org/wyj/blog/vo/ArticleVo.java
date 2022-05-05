package org.wyj.blog.vo;

import lombok.Data;

import java.util.List;

@Data
public class ArticleVo {
    private String id;

    private String title;

    private String summary;

    private int commentCounts;

    private int viewCounts;

    private String author;

    private int weight;

    private String createDate;

    private ArticleBodyVo body;

    private List<TagVo> tags;

    private CategoryVo categoryVo;

}
