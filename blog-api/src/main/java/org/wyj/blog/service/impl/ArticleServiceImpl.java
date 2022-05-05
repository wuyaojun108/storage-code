package org.wyj.blog.service.impl;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wyj.blog.dao.mapper.*;
import org.wyj.blog.dao.pojo.*;
import org.wyj.blog.service.ArticleService;
import org.wyj.blog.service.SysUserService;
import org.wyj.blog.service.TagService;
import org.wyj.blog.service.ThreadService;
import org.wyj.blog.utils.DateUtils;
import org.wyj.blog.vo.*;
import org.wyj.blog.vo.dos.Archive;
import org.wyj.blog.vo.params.ArticleParam;
import org.wyj.blog.vo.params.PageParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service("articleService")
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private TagService tagService;

    @Autowired
    private ArticleBodyMapper articleBodyMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private ThreadService threadService;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public Result listArticle(PageParams pageParams){
        int offset = pageParams.getOffset();
        int pageSize = pageParams.getPageSize();
        List<Article> articles = articleMapper.listArticlePage(offset, pageSize);
        // 更新偏移量
        offset += articles.size();
        List<ArticleVo> articleVoList = copyList(articles, true, true
                , false, true);
        ArticleListPageResult pageResult = new ArticleListPageResult(articleVoList, offset);
        return Result.success(pageResult);
    }

    @Override
    public Result hotArticles(int limit) {
        List<ArticleIdAndTitle> list = articleMapper.hotArticles(limit);
        return Result.success(list);
    }

    @Override
    public Result newArticles(int limit) {
        List<ArticleIdAndTitle> list = articleMapper.newArticles(limit);
        return Result.success(list);
    }

    @Override
    public Result listArchive(int limit) {
        List<Archive> archiveList = articleMapper.listArchive(limit);
        return Result.success(archiveList);
    }

    public Result findArticleById(Long id){
        Article article = articleMapper.selectById(id);
        ArticleVo articleVo = articleToArticleVo(article, true
                , true, true, true);
        // 用户查看文章时，使用线程池技术，更新阅读数
        threadService.updateArticleViewCount(articleMapper, article);
        return Result.success(articleVo);
    }


    // 发布文章，涉及到的表：ms_article、ms_tag、ms_category
    @Override
    @Transactional
    public Result publish(ArticleParam articleParam) {
        // 文章信息
        Article article = new Article();
        article.setAuthorId(Long.parseLong(articleParam.getAuthorId()));
        article.setWeight(Article.Article_Common);
        article.setViewCounts(0);
        article.setTitle(articleParam.getTitle());
        article.setSummary(articleParam.getSummary());
        article.setCommentCounts(0);
        article.setCreateDate(System.currentTimeMillis());
        Long categoryId = categoryMapper.getCategoryIdByName(articleParam.getCategoryName());
        article.setCategoryId(categoryId);
        articleMapper.insert(article);
        // 标签信息
        List<String> tagNameList = articleParam.getTagName();
        List<TagVo> tagVoList = tagMapper.findTagsByTagNameList(tagNameList);
        if (tagVoList != null) {
            for (TagVo tag : tagVoList) {
                Long articleId = article.getId();
                ArticleTag articleTag = new ArticleTag();
                Long id = articleTagMapper.getMaxId();
                articleTag.setArticleId(id + 1);
                articleTag.setArticleId(articleId);
                articleTag.setTagId(Long.valueOf(tag.getId()));
                articleTagMapper.insert(articleTag);
            }
        }
        // 文章内容信息
        ArticleBody articleBody = new ArticleBody();
        Long bodyId = articleBodyMapper.getMaxId();
        articleBody.setId(bodyId + 1);
        articleBody.setArticleId(article.getId());
        articleBody.setContent(articleParam.getContent());
        articleBody.setContentHtml(articleParam.getContentHtml());
        articleBodyMapper.insert(articleBody);
        article.setBodyId(articleBody.getId());
        articleMapper.updateById(article);
        // 返回结果给前端
        HashMap<String, String> map = new HashMap<>();
        map.put("id", article.getId().toString());

        // 清除缓存
        cleanCache();
        return Result.success(map);
    }

    @Transactional
    public void cleanCache(){
        // 获取当前ms_article表中的最大偏移量，
        ArticleMessage articleMessage = new ArticleMessage(
                articleMapper.selectCount(null) - 1);
        rocketMQTemplate.convertAndSend("spring-boot-test1", articleMessage);
    }

    private List<ArticleVo> copyList(List<Article> records, boolean isTag
            , boolean isAuthor, boolean isBody, boolean isCategory) {
        ArrayList<ArticleVo> articleVoList = new ArrayList<>();
        for (Article article : records) {
            articleVoList.add(articleToArticleVo(article, isTag, isAuthor
                    , isBody, isCategory));
        }
        return articleVoList;
    }

    private ArticleVo articleToArticleVo(Article article, boolean isTag
            , boolean isAuthor, boolean isBody, boolean isCategory) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);
        articleVo.setId(article.getId().toString());
        // 把时间从时间戳格式转换为 yyyy-MM-dd HH:mm 的格式
//        //articleVo.setCreateDate(new DateTime(article.getCreateDate())
//                .toString("yyyy-MM-dd HH:mm"));
        articleVo.setCreateDate(DateUtils.dateToStr(article.getCreateDate()
                , "yyyy-MM-dd HH:mm"));
        // 设置作者名称
        if (isAuthor) {
            articleVo.setAuthor(sysUserService.findUserById(article.getAuthorId())
                    .getNickname());
        }
        // 设置标签信息
        if (isTag) {
            articleVo.setTags(tagService.findTagsByArticleId(article.getId()));
        }
        // 设置文章内容
        if (isBody){
            ArticleBody articleBody = articleBodyMapper.selectById(article.getBodyId());
            ArticleBodyVo articleBodyVo = new ArticleBodyVo();
            articleBodyVo.setContent(articleBody.getContent());
            articleBodyVo.setContentHtml(articleBody.getContentHtml());
            articleVo.setBody(articleBodyVo);
        }
        // 设置文章分类信息
        if(isCategory){
            Category category = categoryMapper.selectById(article.getCategoryId());
            articleVo.setCategoryVo(new CategoryVo(category.getId().toString()
                    , category.getAvatar(), category.getCategoryName()));
        }
        return articleVo;
    }

}
