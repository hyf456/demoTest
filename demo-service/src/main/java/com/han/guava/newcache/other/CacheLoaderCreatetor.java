package com.han.guava.newcache.other;

import com.google.common.base.Optional;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: hanyf
 * @Description: 加载类
 * @Date: 2018/8/28 17:09
 */
@Slf4j
public class CacheLoaderCreatetor {
    public static com.google.common.cache.CacheLoader<String, Employee> createCacheLoader() {
        return new com.google.common.cache.CacheLoader<String, Employee>() {
            @Override
            public Employee load(String key) throws Exception {
                log.info("加载创建key:" + key);
                return new Employee(key, key + "dept", key + "id");
            }
        };
    }

    public static com.google.common.cache.CacheLoader<String, Employee> createNUllCacheLoader() {
        return new com.google.common.cache.CacheLoader<String, Employee>() {
            @Override
            public Employee load(String key) throws Exception {
                log.info("加载创建key:" + key);
                if (key.equals("null")) {
                    return null;
                }
                return new Employee(key, key + "dept", key + "id");
            }
        };
    }

    public static com.google.common.cache.CacheLoader<String, Optional<Employee>> createNullValueUseOptionalCacheLoader() {
        return new com.google.common.cache.CacheLoader<String, Optional<Employee>>() {
            @Override
            public Optional<Employee> load(String key) throws Exception {
                log.info("加载创建key:" + key);
                if (key.equals("null")) {
                    return Optional.fromNullable(null);
                } else {
                    return Optional.fromNullable( new Employee(key, key + "dept", key + "id"));
                }
            }


        };
    }
}
