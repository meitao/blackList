package com.kingstar.bw.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: meitao
 * @Description: 本地内存数据库
 * @Date: 20-8-26 下午2:19
 * @Version: 1.0
 */
public class LocalData {

    private static Map<String, Map> map = new ConcurrentHashMap<>();

    /**
     * 获取对应的结果集
     *
     * @param key
     * @return
     */
    public static Map getCollection(String key) {
        return map.get(key);
    }


    /**
     * 设置结果
     *
     * @param key
     * @param param
     * @return
     */
    public static Map setCollection(String key, Map param) {
        return map.put(key, param);
    }


}
