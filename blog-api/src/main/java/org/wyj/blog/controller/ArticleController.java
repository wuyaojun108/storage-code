package org.wyj.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.wyj.blog.cache.Cache;
import org.wyj.blog.log.LogAnnotation;
import org.wyj.blog.service.ArticleService;
import org.wyj.blog.vo.Result;
import org.wyj.blog.vo.params.ArticleParam;
import org.wyj.blog.vo.params.PageParams;

/**
 * 处理文章模块的控制器
 */
@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 文章分页列表
     * @param pageParams 分页参数
     * @return 分页列表
     */
    @Cache(name = "获取文章列表")
    @LogAnnotation(module = "文章", operation = "获取文章列表")
    @PostMapping()
    public Result listArticles(@RequestBody PageParams pageParams) {
        return articleService.listArticle(pageParams);
    }

    @Value("${hotArticleLimit}")
    private String hotArticleLimit;
    /**
     * 获取最热文章
     */
    @LogAnnotation(module = "文章", operation = "获取最热文章")
    @PostMapping("/hot")
    public Result hotArticles() {
        return articleService.hotArticles(Integer.parseInt(hotArticleLimit));
    }

    @Value("${newArticleLimit}")
    private String newArticleLimit;

    /**
     * 获取最新文章
     * @return 返回最新文章列表
     */
    @LogAnnotation(module = "文章", operation = "获取最新文章")
    @PostMapping("/new")
    public Result newArticles() {
        return articleService.newArticles(Integer.parseInt(newArticleLimit));
    }

    /**
     * 限制查询结果中年月的个数
     */

    @Value("${articleListArchiveLimit}")
    private String articleListArchiveLimit;

    /**
     * 文章归档，根据文章创建的年和月来组织文章列表
     * @return 返回文章归档列表
     */
    @LogAnnotation(module = "文章", operation = "文章归档")
    @PostMapping("/listArchive")
    public Result listArchive() {
        return articleService.listArchive(Integer.parseInt(articleListArchiveLimit));
    }

    /**
     * 获取某篇文章的内容
     * @param id 文章id
     * @return 返回文章内容
     */
    @LogAnnotation(module = "文章", operation = "获取某篇文章的内容")
    @PostMapping("view/{id}")
    public Result findArticleById(@PathVariable("id") Long id) {
        return articleService.findArticleById(id);
    }

    /**
     * 发布文章
     */
    @LogAnnotation(module = "文章", operation = "发布文章")
    @PostMapping("/publish")
    public Result publish(@RequestBody ArticleParam articleParam) {
        return articleService.publish(articleParam);
    }
}
