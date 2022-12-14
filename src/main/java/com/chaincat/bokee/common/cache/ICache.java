package com.chaincat.bokee.common.cache;

import java.util.List;

/**
 * 缓存接口
 * @author Chain
 */
public interface ICache {

    /**
     * 设置缓存
     * @param key 键
     * @param value 值
     */
    void set(String key, String value);

    /**
     * 设置缓存
     * @param key 键
     * @param value 值
     * @param expireSeconds 过期秒数
     */
    void set(String key, String value, Long expireSeconds);

    /**
     * 获取缓存
     * @param key 键
     * @param clazz 对象类型
     * @return 对象
     */
    <T> T getObj(String key, Class<T> clazz);

    /**
     * 获取缓存
     * @param key 键
     * @param clazz 列表类型
     * @return 列表
     */
    <T> List<T> getList(String key, Class<T> clazz);

    /**
     * 删除缓存
     * @param key 键
     */
    void delete(String key);
}
