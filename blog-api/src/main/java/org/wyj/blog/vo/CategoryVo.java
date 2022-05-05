package org.wyj.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryVo {
    private String id;
    private String avatar;
    private String categoryName;
}
