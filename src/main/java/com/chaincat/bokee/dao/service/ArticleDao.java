package com.chaincat.bokee.dao.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chaincat.bokee.dao.mapper.ArticleMapper;
import com.chaincat.bokee.entity.Article;
import com.chaincat.bokee.entity.ArticleContent;
import com.chaincat.bokee.entity.extend.ArticleJoin;
import com.chaincat.bokee.vo.CategoryVo;
import com.github.yulichang.query.MPJQueryWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author Chain
 */
@Service
@RequiredArgsConstructor
public class ArticleDao {

    private final ArticleMapper articleMapper;

    public Page<Article> list(Page<Article> param, String categoryName) {
        return articleMapper.selectPage(param, new LambdaQueryWrapper<Article>()
                .eq(StringUtils.hasLength(categoryName), Article::getCategoryName, categoryName));
    }

    public ArticleJoin detail(Long id) {
        return articleMapper.selectJoinOne(ArticleJoin.class, new MPJLambdaWrapper<Article>()
                .selectAll(Article.class)
                .selectAs(ArticleContent::getContent, ArticleJoin::getContent)
                .innerJoin(ArticleContent.class, ArticleContent::getId, Article::getId)
                .eq(Article::getId, id));
    }

    public List<Article> allTag() {
        return articleMapper.selectJoinList(Article.class, new MPJLambdaWrapper<Article>()
                .select(Article::getTags));
    }

    public List<CategoryVo> allCategory() {
        return articleMapper.selectJoinList(CategoryVo.class, new MPJQueryWrapper<Article>()
                .select("category_name")
                .select("COUNT(1) as count")
                .groupBy("category_name"));
    }

    public long countAll() {
        return articleMapper.selectCount(null);
    }

    public Article findById(Long id) {
        return articleMapper.selectById(id);
    }

    public int insert(Article article) {
        return articleMapper.insert(article);
    }

    public int update(Article article) {
        return articleMapper.updateById(article);
    }

    public int delete(Long id) {
        return articleMapper.deleteById(id);
    }
}
