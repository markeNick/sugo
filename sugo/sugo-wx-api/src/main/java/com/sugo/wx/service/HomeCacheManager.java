package com.sugo.wx.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HomeCacheManager {
    public static final  boolean ENABLE = false;
    public static final String INDEX = "index";
    public static final String CATALOG = "catalog";
    public static final String GOODS = "goods";

    private static ConcurrentHashMap<String, Map<String, Object>> cacheDataList = new ConcurrentHashMap<>();

    /**
     * 缓存首页数据
     * @param cacheKey
     * @param data
     */
    public static void loadData(String cacheKey, Map<String, Object> data) {
        Map<String, Object> cacheData = cacheDataList.get(cacheKey);

        // 有记录先丢弃
        if (cacheData != null) {
            cacheData.remove(cacheKey);
        }

        cacheData = new HashMap<>();

        cacheData.putAll(data);
        cacheData.put("isCache", true);

        // 设置有效期
        cacheData.put("expireTime", LocalDateTime.now().plusMinutes(10));
        cacheDataList.put(cacheKey, cacheData);
    }

    /**
     * 判断缓存是否有数据
     * @param cacheKey
     * @return
     */
    public static boolean hasData(String cacheKey) {
        if(!ENABLE) {
            return false;
        }

        Map<String, Object> cacheData = cacheDataList.get(cacheKey);
        if(cacheData == null) {
            return false;
        } else {
            LocalDateTime expire = (LocalDateTime) cacheData.get("expireTime");
            if(expire.isBefore(LocalDateTime.now())) {
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     * 清除所有缓存
     */
    public static void clearAll() {
        cacheDataList = new ConcurrentHashMap<>();
    }

    /**
     * 清除指定缓存
     * @param cacheKey
     */
    public static void clear(String cacheKey) {
        Map<String, Object> cacheData = cacheDataList.get(cacheKey);
        if(cacheData != null) {
            cacheDataList.remove(cacheKey);
        }
    }
}

























