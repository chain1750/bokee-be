package com.chaincat.bokee.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chaincat.bokee.common.cache.ICache;
import com.chaincat.bokee.common.constant.CacheConstant;
import com.chaincat.bokee.common.data.PageResult;
import com.chaincat.bokee.common.exception.BusinessException;
import com.chaincat.bokee.common.utils.BeanCopyUtils;
import com.chaincat.bokee.dao.service.ArticleDao;
import com.chaincat.bokee.entity.Article;
import com.chaincat.bokee.entity.extend.ArticleJoin;
import com.chaincat.bokee.enums.error.ArticleError;
import com.chaincat.bokee.service.IArticleService;
import com.chaincat.bokee.vo.ArticleVo;
import com.chaincat.bokee.vo.CategoryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 文章业务实现
 * @author Chain
 */
@Slf4j
@Service
public class ArticleServiceImpl implements IArticleService {

    private static final String TAG_SPLIT = ";";

    private final ArticleDao articleDao;

    private final ICache cache;

    public ArticleServiceImpl(ArticleDao articleDao, @Qualifier(CacheConstant.REDIS) ICache cache) {
        this.articleDao = articleDao;
        this.cache = cache;
    }

    @Override
    public void list(PageResult<ArticleVo> pageResult, String categoryName) {
        Page<Article> param = Page.of(pageResult.getCurrent(), pageResult.getSize());
        Page<Article> result = articleDao.list(param, categoryName);
        pageResult.construct(result, ArticleVo.class, null);

        Map<Long, Article> map = result.getRecords().stream().collect(Collectors.toMap(Article::getId, Function.identity()));
        pageResult.getRecords().forEach(e -> {
            e.setTagList(Arrays.asList(map.get(e.getId()).getTags().split(TAG_SPLIT)));
            e.setYear(e.getCreateTime().getYear());
            e.setMonth(e.getCreateTime().getMonthValue());
        });
    }

    @Override
    public ArticleVo detail(Long id) {
        String cacheKey = String.format(CacheConstant.CACHE_KEY_ARTICLE_DETAIL, id);
        ArticleVo cacheObj = cache.getObj(cacheKey, ArticleVo.class);
        if (!Objects.isNull(cacheObj)) {
            return cacheObj;
        }
        ArticleJoin articleJoin = articleDao.detail(id);
        if (Objects.isNull(articleJoin)) {
            throw new BusinessException(ArticleError.ARTICLE_NOT_FOUND);
        }

        ArticleVo articleVo = BeanCopyUtils.covert(articleJoin, ArticleVo.class);
        articleVo.setTagList(Arrays.asList(articleJoin.getTags().split(TAG_SPLIT)));
        cache.set(cacheKey, JSON.toJSONString(articleVo), 60L);
        return articleVo;
    }

    @Override
    public List<String> allTag() {
        List<String> cacheList = cache.getList(CacheConstant.CACHE_KEY_ARTICLE_ALL_TAG, String.class);
        if (!CollectionUtils.isEmpty(cacheList)) {
            return cacheList;
        }
        List<Article> articleList = articleDao.allTag();
        List<String> allTag = new ArrayList<>();
        articleList.forEach(article -> {
            List<String> tagList = Arrays.asList(article.getTags().split(TAG_SPLIT));
            allTag.addAll(tagList);
        });
        List<String> result = allTag.stream().distinct().collect(Collectors.toList());
        cache.set(CacheConstant.CACHE_KEY_ARTICLE_ALL_TAG, JSON.toJSONString(result), 600L);
        return result;
    }

    @Override
    public List<CategoryVo> allCategory() {
        cache.delete(CacheConstant.CACHE_KEY_ARTICLE_ALL_CATEGORY);
        List<CategoryVo> cacheList = cache.getList(CacheConstant.CACHE_KEY_ARTICLE_ALL_CATEGORY, CategoryVo.class);
        if (!CollectionUtils.isEmpty(cacheList)) {
            return cacheList;
        }
        List<CategoryVo> categoryVoList = articleDao.allCategory();
        categoryVoList.add(0, new CategoryVo("", articleDao.countAll()));
        cache.set(CacheConstant.CACHE_KEY_ARTICLE_ALL_CATEGORY, JSON.toJSONString(categoryVoList), 600L);
        return categoryVoList;
    }
}
