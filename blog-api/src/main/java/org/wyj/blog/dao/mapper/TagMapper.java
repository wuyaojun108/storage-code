package org.wyj.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.wyj.blog.dao.pojo.Tag;
import org.wyj.blog.vo.HotTag;
import org.wyj.blog.vo.TagVo;

import java.util.List;

public interface TagMapper extends BaseMapper<Tag> {

    /**
     * 根据文章ID来获取文章的标签
     */
    @Select("SELECT `id`, `avatar`, `tag_name` as `tagName`\n" +
            "FROM `ms_tag`\n" +
            "WHERE `id` in (\n" +
            "    SELECT `tag_id`\n" +
            "    FROM `ms_article_tag`\n" +
            "    WHERE `article_id` = #{articleId}\n" +
            ")")
    List<Tag> findTagsByArticleId(Long articleId);

    /**
     * 查询热门标签，就是一个标签对应的文章数比较多的标签
     */
    @Select("SELECT tag.id, tag.tag_name tagName, count(at.article_id) co " +
            "FROM ms_tag tag " +
            "    LEFT JOIN ms_article_tag at " +
            "    ON tag.id = at.tag_id " +
            "GROUP BY tag.id, tag.tag_name " +
            "ORDER BY co DESC " +
            "LIMIT #{limit}")
    List<HotTag> findHotTags(int limit);

    /**
     * 获取最热标签，ms_article_tag，文章标签中间表，描述了一个文章对应的标签，通过计算每个标签对应几篇文章，
     * 统计出最热标签，但是在这里只能获取最热标签的标签ID，还需要进一步处理
     */
    @Select("SELECT tag_id\n" +
            "FROM (\n" +
            "    SELECT tag_id, count(1) co\n" +
            "    FROM ms_article_tag\n" +
            "    GROUP BY tag_id\n" +
            "    ORDER BY co desc\n" +
            ") t1\n" +
            "LIMIT #{limit}")
    List<Long> findHotTagIdList(int limit);

    /**
     * 标签标签ID列表，查询标签信息
     */
    @Select("<script>\n" +
            "    SELECT id, avatar, tag_name as tagName\n" +
            "    FROM ms_tag\n" +
            "    WHERE id in \n" +
            "    <foreach collection='tagIdList' open='(' item='tagId' " +
            "        separator=',' close=')'>#{tagId} " +
            "    </foreach>\n" +
            "</script>")
    List<Tag> findTagsByTagIdList(List<Long> tagIdList);

    /**
     * 根据标签名称列表，获取标签信息
     */
    @Select("<script>\n" +
            "    SELECT id, tag_name as tagName\n" +
            "    FROM ms_tag\n" +
            "    WHERE tag_name IN \n" +
            "    <foreach collection='tagNameList' open='(' item='tagName' " +
            "        separator=',' close=')'>#{tagName}" +
            "    </foreach>\n" +
            "</script>")
    List<TagVo> findTagsByTagNameList(List<String> tagNameList);


}
