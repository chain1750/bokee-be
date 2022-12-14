package com.chaincat.bokee.common.constant;

/**
 * 缓存Bean名称
 * @author Chain
 */
public interface CacheConstant {

    String REDIS = "redis";

    String LOCAL = "local";

    String CACHE_KEY_ARTICLE_DETAIL = "article.%d";

    String CACHE_KEY_ARTICLE_ALL_TAG = "article.all.tag";

    String CACHE_KEY_ARTICLE_ALL_CATEGORY = "article.all.category";
}
