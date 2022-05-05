package org.wyj.blog.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.wyj.blog.dao.mapper.ArticleMapper;
import org.wyj.blog.dao.pojo.Article;

@Component
@Slf4j
public class ThreadService {

    @Async("taskExecutor")
    public void updateArticleViewCount(ArticleMapper articleMapper, Article article) {
        int affectedRow = articleMapper.updateViewCounts(article.getId()
                , article.getViewCounts());
        if (affectedRow == 1) {
            log.info("文章ID: {}, 阅读次数更新成功", article.getId());
        } else {
            log.info("文章ID: {}, 阅读次数更新失败", article.getId());
        }
    }
}
