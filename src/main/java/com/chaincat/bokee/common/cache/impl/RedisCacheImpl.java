package com.chaincat.bokee.common.cache.impl;

import com.alibaba.fastjson.JSON;
import com.chaincat.bokee.common.cache.ICache;
import com.chaincat.bokee.common.constant.CacheConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Redis缓存实现
 * @author Chain
 */
@Component(CacheConstant.REDIS)
@RequiredArgsConstructor
public class RedisCacheImpl implements ICache {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(String key, String value, Long expireSeconds) {
        redisTemplate.opsForValue().set(key, value, expireSeconds, TimeUnit.SECONDS);
    }

    @Override
    public <T> T getObj(String key, Class<T> clazz) {
        return JSON.parseObject(redisTemplate.opsForValue().get(key), clazz);
    }

    @Override
    public <T> List<T> getList(String key, Class<T> clazz) {
        return JSON.parseArray(redisTemplate.opsForValue().get(key), clazz);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
