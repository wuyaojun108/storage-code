package org.wyj.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.wyj.blog.dao.pojo.Article;
import org.wyj.blog.vo.ArticleIdAndTitle;
import org.wyj.blog.vo.dos.Archive;

import java.util.List;

public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 从文章的创建时间中提取出年和月的信息，计算出每个年月下的文章的篇数
     * @param limit 限制查询出的年月的个数
     * @return 返回文章归档列表
     */
    @Select("SELECT year, month, count(1) count\n" +
            "FROM (\n" +
            "    SELECT id\n" +
            "        , from_unixtime(substring(create_date, 1, 10), '%Y') year\n" +
            "        , from_unixtime(substring(create_date, 1, 10), '%m') month\n" +
            "    FROM ms_article\n" +
            ") t1\n" +
            "GROUP BY year, month\n" +
            "ORDER BY year DESC, month DESC\n" +
            "LIMIT #{limit}")
    List<Archive> listArchive(int limit);

    // 阅读次数加1
    @Update("UPDATE ms_article " +
            "SET view_counts = #{viewCounts} + 1 " +
            "WHERE id = #{articleId} and view_counts = #{viewCounts}")
    int updateViewCounts(Long articleId, int viewCounts);

    /**
     * 查询分页列表
     * @param offset 偏移量
     * @param pageSize 页面大小
     * @return 分页列表
     */
    @Select("SELECT id, title, summary, comment_counts commentCounts " +
            "    , view_counts viewCounts, author_id authorId" +
            "    , body_id bodyId, category_id categoryId, weight " +
            "    , create_date createDate " +
            "FROM ms_article " +
            "ORDER BY create_date " +
            "LIMIT #{offset}, #{pageSize}")
    List<Article> listArticlePage(int offset, int pageSize);

    /**
     * 获取最热文章，根据浏览次数进行倒序排序，选择参数指定的条数，返回给前端
     * @param limit 限制获取的条数
     * @return 最热文章列表
     */
    @Select("SELECT id, title " +
            "FROM ms_article " +
            "ORDER BY view_counts DESC " +
            "LIMIT #{limit}")
    List<ArticleIdAndTitle> hotArticles(int limit);

    /**
     * 获取最新文章，根据创建时间进行倒序排序，选择参数指定的条数，返回给前端
     * @param limit 限制获取的条数
     * @return 最新文章列表
     */
    @Select("SELECT id, title " +
            "FROM ms_article " +
            "ORDER BY create_date DESC " +
            "LIMIT #{limit}")
    List<ArticleIdAndTitle> newArticles(int limit);

}
