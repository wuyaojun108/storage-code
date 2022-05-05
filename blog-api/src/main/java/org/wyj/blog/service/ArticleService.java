package org.wyj.blog.service;

import org.wyj.blog.vo.Result;
import org.wyj.blog.vo.params.ArticleParam;
import org.wyj.blog.vo.params.PageParams;

public interface ArticleService {
    /**
     * 获取分页文章列表
     * @param pageParams 分页信息
     * @return 分页后的文章列表
     */
    Result listArticle(PageParams pageParams);

    /**
     * 获取最热文章
     * @param limit 限制获取出的条数
     * @return 最热文章列表
     */
    Result hotArticles(int limit);

    /**
     * 获取最新文章
     * @param limit 限制获取出的条数
     * @return 最新文章列表
     */
    Result newArticles(int limit);

    /**
     * 文章归档，根据年和月来组织文章列表
     * @param limit 限制查询出的年月的个数
     * @return 返回文章归档列表
     */
    Result listArchive(int limit);

    /**
     * 获取某篇文章的内容
     */
    Result findArticleById(Long id);

    /**
     * 发布文章
     */
    Result publish(ArticleParam articleParam);
}
