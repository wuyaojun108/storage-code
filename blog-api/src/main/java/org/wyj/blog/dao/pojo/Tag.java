package org.wyj.blog.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Tag {
    /**
     * 标签ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 标签图片
     */
    private String avatar;
    /**
     * 标签名称
     */
    private String tagName;
}
