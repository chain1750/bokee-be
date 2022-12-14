package com.chaincat.bokee.common.cache.impl;

import com.alibaba.fastjson.JSON;
import com.chaincat.bokee.common.cache.ICache;
import com.chaincat.bokee.common.constant.CacheConstant;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地缓存实现
 * @author Chain
 */
@Component(CacheConstant.LOCAL)
public class LocalCacheImpl implements ICache {

    private final int capacity = 256;

    private final Map<String, ItemNode> cache;

    private final ItemNode head;

    private final ItemNode tail;

    private int size;

    public LocalCacheImpl() {
        this.cache = new ConcurrentHashMap<>(capacity);
        this.head = new ItemNode();
        this.tail = new ItemNode();
        this.size = 0;

        head.next = tail;
        tail.prev = head;
    }

    @Override
    public void set(String key, String value) {
        set(key, value, 0L);
    }

    @Override
    public void set(String key, String value, Long expireSeconds) {
        // 不设置过期时间为0
        // 设置过期时间为当前毫秒数+过期时间
        expireSeconds = expireSeconds == 0 ? 0L : expireSeconds * 1000 + System.currentTimeMillis();

        // 缓存数据
        Item data = new Item(value, expireSeconds);

        // 从缓存中获取数据节点
        ItemNode oldNode = cache.get(key);

        // 若没有重复key，则保存缓存数据，并添加置头部
        // 如果超出容量，则删除尾部
        if (oldNode == null) {
            ItemNode newNode = new ItemNode(key, data);
            cache.put(key, newNode);
            addToHead(newNode);
            ++size;
            if (size > capacity) {
                ItemNode tail = removeTail();
                cache.remove(tail.key);
                --size;
            }
        }
        // 若重复key，则替换缓存数据，并移动置头部
        else {
            oldNode.data = data;
            moveToHead(oldNode);
        }
    }

    @Override
    public <T> T getObj(String key, Class<T> clazz) {
        return JSON.parseObject(get(key), clazz);
    }

    @Override
    public <T> List<T> getList(String key, Class<T> clazz) {
        return JSON.parseArray(get(key), clazz);
    }

    private String get(String key) {
        ItemNode node = cache.get(key);
        if (node == null) {
            return null;
        }
        // 如果过期，则删除缓存
        Long expireSeconds = node.data.expireSeconds;
        if (expireSeconds != 0 && expireSeconds < System.currentTimeMillis()) {
            removeNode(node);
            cache.remove(node.key);
            return null;
        }
        moveToHead(node);
        return node.data.value;
    }

    @Override
    public void delete(String key) {

    }

    /**
     * 将节点添加置链表头部
     */
    private void addToHead(ItemNode node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    /**
     * 删除节点
     */
    private void removeNode(ItemNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    /**
     * 将节点移动置头部
     */
    private void moveToHead(ItemNode node) {
        removeNode(node);
        addToHead(node);
    }

    /**
     * 删除尾部节点
     */
    private ItemNode removeTail() {
        ItemNode node = tail.prev;
        removeNode(node);
        return node;
    }

    /**
     * 节点
     */
    static class ItemNode {
        String key;
        Item data;
        ItemNode prev;
        ItemNode next;

        ItemNode() {}
        ItemNode(String k, Item d) {
            key = k;
            data = d;
        }
    }

    /**
     * 数据
     */
    static class Item {
        String value;
        Long expireSeconds;

        Item(String v, Long e) {
            value = v;
            expireSeconds = e;
        }
    }
}
