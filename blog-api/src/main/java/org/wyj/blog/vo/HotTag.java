package org.wyj.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotTag {
    /**
     * 标签ID
     */
    private String id;

    /**
     * 标签名称
     */
    private String tagName;

    /**
     * 一个标签ID对应的文章数量
     */
    private String co;
}
