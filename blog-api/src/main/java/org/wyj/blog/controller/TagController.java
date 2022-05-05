package org.wyj.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wyj.blog.log.LogAnnotation;
import org.wyj.blog.service.TagService;
import org.wyj.blog.vo.Result;

@RestController
@RequestMapping("/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * 限制查询出来的热门标签的个数
     */
    @Value("${hotTagLimit}")
    private String hotTagLimit;

    /**
     * 查询热门标签
     */
    @LogAnnotation(module = "标签", operation = "获取热门标签")
    @GetMapping("/hot")
    public Result hot() {
        return tagService.findHotTags(Integer.parseInt(hotTagLimit));
    }

    /**
     * 获取所有标签
     */
    @LogAnnotation(module = "标签", operation = "获取所有标签")
    @GetMapping("/all")
    public Result getAllTags() {
        return tagService.getAllTags();
    }
}
