package org.wyj.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.wyj.blog.dao.pojo.Category;

public interface CategoryMapper extends BaseMapper<Category> {

    @Select("SELECT id FROM ms_category WHERE category_name = #{categoryName}")
    Long getCategoryIdByName(String categoryName);
}
