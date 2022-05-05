package org.wyj.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleIdAndTitle {

    /**
     * 用户ID
     */
    private String id;

    /**
     * 标题
     */
    private String title;

}
