package com.han.commom.util;

import com.jayway.jsonpath.*;
import com.jayway.jsonpath.spi.cache.CacheProvider;
import com.jayway.jsonpath.spi.cache.LRUCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * @Description JsonPath处理JSON字符串的工具方法
 * @ClassName JsonPathUtils
 * @Author hanyunfei1
 * @Date 2025/8/26 20:28
 * @Version 2.0
 **/
@Slf4j
public class JsonPathUtils {

    /**
     * 缓存大小
     */
    private static final int CACHE_SIZE = 1000;

    /**
     * 默认配置
     */
    private static final Configuration DEFAULT_CONFIGURATION;

    /**
     * 初始化配置
     */
    static {
        // 设置缓存提供者
        CacheProvider.setCache(new LRUCache(CACHE_SIZE));

        // 初始化默认配置
        DEFAULT_CONFIGURATION = Configuration.builder()
                .options(Option.DEFAULT_PATH_LEAF_TO_NULL)  // 路径不存在时返回null而不是抛出异常
                .options(Option.SUPPRESS_EXCEPTIONS)        // 抑制异常
                .build();

    }

    /**
     * 获取解析上下文
     *
     * @return 解析上下文
     */
    private static ParseContext getParseContext() {
        return JsonPath.using(DEFAULT_CONFIGURATION);
    }

    /**
     * 解析JSON字符串
     *
     * @param json JSON字符串
     * @return 文档上下文，如果解析失败则返回空
     */
    @SuppressWarnings("swallow-exception: 解析失败,不处理异常")
    public static Optional<DocumentContext> parse(String json) {
        if (StringUtils.isBlank(json)) {
            return Optional.empty();
        }

        try {
            return Optional.of(getParseContext().parse(json));
        } catch (Exception e) {
            LoggerUtils.error(log, "解析JSON字符串失败: {}", json, e);
            return Optional.empty();
        }
    }

    /**
     * 读取JSON路径的值（字符串类型）
     *
     * @param json     JSON字符串
     * @param jsonPath JSON路径
     * @return 路径对应的值，如果不存在或发生异常则返回null
     */
    public static String readString(String json, String jsonPath) {
        return read(json, jsonPath, String.class);
    }

    /**
     * 读取JSON路径的值（整数类型）
     *
     * @param json     JSON字符串
     * @param jsonPath JSON路径
     * @return 路径对应的值，如果不存在或发生异常则返回null
     */
    public static Integer readInteger(String json, String jsonPath) {
        return read(json, jsonPath, Integer.class);
    }

    /**
     * 读取JSON路径的值（长整数类型）
     *
     * @param json     JSON字符串
     * @param jsonPath JSON路径
     * @return 路径对应的值，如果不存在或发生异常则返回null
     */
    public static Long readLong(String json, String jsonPath) {
        return read(json, jsonPath, Long.class);
    }

    /**
     * 读取JSON路径的值（布尔类型）
     *
     * @param json     JSON字符串
     * @param jsonPath JSON路径
     * @return 路径对应的值，如果不存在或发生异常则返回null
     */
    public static Boolean readBoolean(String json, String jsonPath) {
        return read(json, jsonPath, Boolean.class);
    }

    /**
     * 读取JSON路径的值（双精度浮点类型）
     *
     * @param json     JSON字符串
     * @param jsonPath JSON路径
     * @return 路径对应的值，如果不存在或发生异常则返回null
     */
    public static Double readDouble(String json, String jsonPath) {
        return read(json, jsonPath, Double.class);
    }

    /**
     * 读取JSON路径的值（Map类型）
     *
     * @param json     JSON字符串
     * @param jsonPath JSON路径
     * @return 路径对应的值，如果不存在或发生异常则返回空Map
     */
    public static Map<String, Object> readMap(String json, String jsonPath) {
        Map<String, Object> result = read(json, jsonPath, Map.class);
        return result != null ? result : Collections.emptyMap();
    }

    /**
     * 读取JSON路径的值（List类型）
     *
     * @param json     JSON字符串
     * @param jsonPath JSON路径
     * @param <T>      列表元素类型
     * @return 路径对应的值，如果不存在或发生异常则返回空List
     */
    public static <T> List<T> readList(String json, String jsonPath) {
        List<T> result = read(json, jsonPath, List.class);
        return result != null ? result : Collections.emptyList();
    }

    /**
     * 读取JSON路径的值（泛型方法）
     *
     * @param json     JSON字符串
     * @param jsonPath JSON路径
     * @param clazz    返回值类型
     * @param <T>      返回值泛型
     * @return 路径对应的值，如果不存在或发生异常则返回null
     */
    @SuppressWarnings("swallow-exception: 读取JSON路径,不处理异常")
    public static <T> T read(String json, String jsonPath, Class<T> clazz) {
        if (StringUtils.isBlank(json) || StringUtils.isBlank(jsonPath)) {
            return null;
        }

        try {
            return parse(json)
                    .map(ctx -> ctx.read(jsonPath, clazz))
                    .orElse(null);
        } catch (Exception e) {
            LoggerUtils.error(log, "JsonPathUtils-read", "读取JSON路径[{}]失败: {}", jsonPath, json, e);
            return null;
        }
    }

    /**
     * 设置JSON路径的值
     *
     * @param json     JSON字符串
     * @param jsonPath JSON路径
     * @param value    要设置的值
     * @return 修改后的JSON字符串，如果发生异常则返回原JSON
     */
    @SuppressWarnings("swallow-exception: 设置JSON路径,不处理异常")
    public static String set(String json, String jsonPath, Object value) {
        if (StringUtils.isBlank(json) || StringUtils.isBlank(jsonPath)) {
            return json;
        }

        try {
            return parse(json)
                    .map(ctx -> {
                        ctx.set(jsonPath, value);
                        return ctx.jsonString();
                    })
                    .orElse(json);
        } catch (Exception e) {
            LoggerUtils.error(log, "JsonPathUtils-set", "设置JSON路径[{}]的值失败: {}", jsonPath, json, e);
            return json;
        }
    }

    /**
     * 批量设置JSON路径的值
     *
     * @param json         JSON字符串
     * @param jsonPathList JSON路径列表
     * @param value        要设置的值
     * @return 修改后的JSON字符串，如果发生异常则返回原JSON
     */
    @SuppressWarnings("swallow-exception: 设置JSON路径,不处理异常")
    public static String set(String json, List<String> jsonPathList, Object value) {
        if (StringUtils.isBlank(json) || jsonPathList == null || jsonPathList.isEmpty()) {
            return json;
        }

        try {
            return parse(json)
                    .map(ctx -> {
                        jsonPathList.forEach(jsonPath -> {
                            try {
                                ctx.set(jsonPath, value);
                            } catch (Exception e) {
                                LoggerUtils.error(log, "设置JSON路径[{}]的值失败", jsonPath, e);
                            }
                        });
                        return ctx.jsonString();
                    })
                    .orElse(json);
        } catch (Exception e) {
            LoggerUtils.error(log, "JsonPathUtils-set", "批量设置JSON路径的值失败: {}", json, e);
            return json;
        }
    }

    /**
     * 删除JSON路径对应的节点
     *
     * @param json     JSON字符串
     * @param jsonPath JSON路径
     * @return 修改后的JSON字符串，如果发生异常则返回原JSON
     */
    @SuppressWarnings("swallow-exception: 删除JSON路径,不处理异常")
    public static String delete(String json, String jsonPath) {
        if (StringUtils.isBlank(json) || StringUtils.isBlank(jsonPath)) {
            return json;
        }

        try {
            return parse(json)
                    .map(ctx -> {
                        ctx.delete(jsonPath);
                        return ctx.jsonString();
                    })
                    .orElse(json);
        } catch (Exception e) {
            LoggerUtils.error(log, "JsonPathUtils-delete", "删除JSON路径[{}]失败: {}", jsonPath, json, e);
            return json;
        }
    }

    /**
     * 批量删除JSON路径对应的节点
     *
     * @param json         JSON字符串
     * @param jsonPathList JSON路径列表
     * @return 修改后的JSON字符串，如果发生异常则返回原JSON
     */
    @SuppressWarnings("swallow-exception: 删除JSON路径,不处理异常")
    public static String delete(String json, List<String> jsonPathList) {
        if (StringUtils.isBlank(json) || jsonPathList == null || jsonPathList.isEmpty()) {
            return json;
        }

        try {
            return parse(json)
                    .map(ctx -> {
                        jsonPathList.forEach(jsonPath -> {
                            try {
                                ctx.delete(jsonPath);
                            } catch (Exception e) {
                                LoggerUtils.error(log, "删除JSON路径[{}]失败", jsonPath, e);
                            }
                        });
                        return ctx.jsonString();
                    })
                    .orElse(json);
        } catch (Exception e) {
            LoggerUtils.error(log, "JsonPathUtils-delete", "批量删除JSON路径失败: {}", json, e);
            return json;
        }
    }

    /**
     * 添加或替换JSON路径对应的节点
     *
     * @param json     JSON字符串
     * @param jsonPath JSON路径
     * @param value    要添加的值
     * @return 修改后的JSON字符串，如果发生异常则返回原JSON
     */
    @SuppressWarnings("swallow-exception: 添加JSON路径,不处理异常")
    public static String add(String json, String jsonPath, Object value) {
        if (StringUtils.isBlank(json) || StringUtils.isBlank(jsonPath)) {
            return json;
        }

        try {
            return parse(json)
                    .map(ctx -> {
                        ctx.add(jsonPath, value);
                        return ctx.jsonString();
                    })
                    .orElse(json);
        } catch (Exception e) {
            LoggerUtils.error(log, "JsonPathUtils-add", "添加JSON路径[{}]的值失败: {}", jsonPath, json, e);
            return json;
        }
    }

    /**
     * 判断JSON路径是否存在
     *
     * @param json     JSON字符串
     * @param jsonPath JSON路径
     * @return 如果路径存在则返回true，否则返回false
     */
    @SuppressWarnings("swallow-exception: 添加JSON路径,不处理异常")
    public static boolean exists(String json, String jsonPath) {
        if (StringUtils.isBlank(json) || StringUtils.isBlank(jsonPath)) {
            return false;
        }

        try {
            return parse(json)
                    .map(ctx -> ctx.read(jsonPath) != null)
                    .orElse(false);
        } catch (Exception e) {
            LoggerUtils.error(log, "JsonPathUtils-exists", "检查JSON路径[{}]是否存在失败: {}", jsonPath, json, e);
            return false;
        }
    }

    /**
     * 将对象转换为JSON字符串
     *
     * @param object 要转换的对象
     * @return JSON字符串，如果转换失败则返回null
     */
    public static String toJsonString(Object object) {
        if (object == null) {
            return null;
        }

        try {
            return getParseContext().parse(object).jsonString();
        } catch (Exception e) {
            LoggerUtils.error(log, "JsonPathUtils-toJsonString", "将对象转换为JSON字符串失败", e);
            return null;
        }
    }

    private static final String VALID_JSON = "{\"name\":\"张三\",\"age\":30,\"isVip\":true,\"score\":98.5,\"address\":{\"city\":\"北京\",\"district\":\"朝阳区\"},\"tags\":[\"Java\",\"Spring\",\"微服务\"]}";

    public static void main(String[] args) {
        // 执行
        List<String> tags = JsonPathUtils.readList(VALID_JSON, "$.tags");
        System.out.println(tags);
        Integer i = JsonPathUtils.readInteger(VALID_JSON, "$.tags.size()");
        System.out.println(i);
    }
}
