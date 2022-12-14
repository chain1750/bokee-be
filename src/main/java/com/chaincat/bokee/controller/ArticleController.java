package com.chaincat.bokee.controller;

import com.chaincat.bokee.common.data.PageResult;
import com.chaincat.bokee.common.data.Result;
import com.chaincat.bokee.service.IArticleService;
import com.chaincat.bokee.vo.ArticleVo;
import com.chaincat.bokee.vo.CategoryVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文章接口
 * @author Chain
 */
@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleController {

    private final IArticleService articleService;

    @GetMapping("/list")
    public Result<PageResult<ArticleVo>> list(@RequestParam(defaultValue = "1") Long current,
                                              @RequestParam(defaultValue = "10") Long size,
                                              @RequestParam(required = false) String categoryName) {
        PageResult<ArticleVo> pageResult = new PageResult<>(current, size);
        articleService.list(pageResult, categoryName);
        return Result.ok(pageResult);
    }

    @GetMapping("/detail/{id}")
    public Result<ArticleVo> detail(@PathVariable Long id) {
        return Result.ok(articleService.detail(id));
    }

    @GetMapping("/tags")
    public Result<List<String>> allTag() {
        return Result.ok(articleService.allTag());
    }

    @GetMapping("/categories")
    public Result<List<CategoryVo>> allCategory() {
        return Result.ok(articleService.allCategory());
    }
}
