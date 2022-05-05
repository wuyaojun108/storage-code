package org.wyj.blog.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleBody {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String content;
    private String contentHtml;
    private Long articleId;
}
