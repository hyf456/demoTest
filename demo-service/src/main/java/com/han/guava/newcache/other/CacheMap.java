package com.han.guava.newcache.other;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: hanyf
 * @Description:
 * @Date: 2018/8/28 17:13
 */
@Slf4j
public class CacheMap {

    /**
     * @Author: hanyf
     * @Description: 使用google guava缓存处理
     * @Date: 2018/8/28 17:14
     */
    private static Cache<String,Object> cache;
    static {
        cache = CacheBuilder.newBuilder().maximumSize(10000)
                .expireAfterWrite(24, TimeUnit.HOURS)
                .initialCapacity(10)
                .removalListener(new RemovalListener<String, Object>() {
                    @Override
                    public void onRemoval(RemovalNotification<String, Object> rn) {
                        if(log.isInfoEnabled()){
                            log.info("被移除缓存{}:{}",rn.getKey(),rn.getValue());
                        }
                    }
                }).build();
    }

    /**
     * @Author: hanyf
     * @Description: 获取缓存
     * @Date: 2018/8/28 17:14
     */
    public  static Object get(String key){
        return StringUtils.isNotEmpty(key)?cache.getIfPresent(key):null;
    }

    /**
     * @Author: hanyf
     * @Description: 放入缓存
     * @Date: 2018/8/28 17:15
     */
    public static void put(String key,Object value){
        if(StringUtils.isNotEmpty(key) && value !=null){
            cache.put(key,value);
        }
    }

    /**
     * @Author: hanyf
     * @Description: 移除缓存
     * @Date: 2018/8/28 17:15
     */
    public static void remove(String key){
        if(StringUtils.isNotEmpty(key)){
            cache.invalidate(key);
        }
    }

    /**
     * @Author: hanyf
     * @Description: 批量删除缓存
     * @Date: 2018/8/28 17:15
     */
    public static void remove(List<String> keys){
        if(keys !=null && keys.size() >0){
            cache.invalidateAll(keys);
        }
    }
}
