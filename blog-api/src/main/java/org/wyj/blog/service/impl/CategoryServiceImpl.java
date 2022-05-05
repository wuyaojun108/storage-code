package org.wyj.blog.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wyj.blog.dao.mapper.CategoryMapper;
import org.wyj.blog.dao.pojo.Category;
import org.wyj.blog.service.CategoryService;
import org.wyj.blog.vo.CategoryVo;
import org.wyj.blog.vo.Result;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    public Result categories() {
        List<Category> categories = categoryMapper.selectList(null);
        return Result.success(copyList(categories));
    }

    private List<CategoryVo> copyList(List<Category> categories) {
        ArrayList<CategoryVo> categoryVoList = new ArrayList<>();
        for (Category category : categories) {
            categoryVoList.add(copy(category));
        }
        return categoryVoList;
    }

    private CategoryVo copy(Category category) {
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        categoryVo.setId(Long.toString(category.getId()));
        return categoryVo;
    }
}
