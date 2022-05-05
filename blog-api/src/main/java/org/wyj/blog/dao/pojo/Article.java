package org.wyj.blog.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Article {
    /**
     * 表示文章置顶
     */
    public static final int Article_TOP = 1;

    /**
     * 表示文章不是置顶的
     */
    public static final int Article_Common = 0;

    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 简介
     */
    private String summary;

    /**
     * 评论次数
     */
    private Integer commentCounts;

    /**
     * 浏览次数
     */
    private Integer viewCounts;
    /**
     * 作者ID
     */
    private Long authorId;

    /**
     * 内容ID
     */
    private Long bodyId;

    /**
     * 类别ID
     */
    private Long categoryId;

    /**
     * 是否置顶
     */
    private int weight = Article_Common;

    /**
     * 创建时间
     */
    private Long createDate;
}
