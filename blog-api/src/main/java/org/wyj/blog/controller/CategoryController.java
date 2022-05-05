package org.wyj.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wyj.blog.log.LogAnnotation;
import org.wyj.blog.service.CategoryService;
import org.wyj.blog.vo.Result;

@RestController
@RequestMapping("categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 获取所有的文章分类
     */
    @LogAnnotation(module = "分类", operation = "获取分类列表")
    @GetMapping
    public Result categories() {
        return categoryService.categories();
    }
}
