package org.wyj.blog.vo.params;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageParams {

    /**
     * 页码
     */
    private int offset = 0;

    /**
     * 页面中元素个数
     */
    private int pageSize = 10;
}
