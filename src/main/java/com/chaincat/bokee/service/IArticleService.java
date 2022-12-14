package com.chaincat.bokee.service;

import com.chaincat.bokee.common.data.PageResult;
import com.chaincat.bokee.vo.ArticleVo;
import com.chaincat.bokee.vo.CategoryVo;

import java.util.List;

/**
 * 文章业务接口
 * @author Chain
 */
public interface IArticleService {

    /**
     * 文章列表
     * @param pageResult 分页参数
     * @param categoryName 分类名称
     */
    void list(PageResult<ArticleVo> pageResult, String categoryName);

    /**
     * 文章详情
     * @param id 文章ID
     * @return 文章
     */
    ArticleVo detail(Long id);

    /**
     * 所有标签
     * @return 标签列表
     */
    List<String> allTag();

    /**
     * 所有分类
     * @return 分类列表
     */
    List<CategoryVo> allCategory();
}
