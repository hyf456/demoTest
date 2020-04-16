package com.han.spel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Gson工具类
 *
 * @author: hanyf
 * @date: 2019-09-26 12:18
 */
public class GsonUtils {
    private static final Logger logger = LoggerFactory.getLogger(GsonUtils.class);
    private static Gson gson = null;
    private static Gson gsonSerializeNulls = null;

    static {
        if (gson == null) {
            gson = GsonBuilderUtils.getGson(false);
        }
        if (gsonSerializeNulls == null) {
            gsonSerializeNulls = GsonBuilderUtils.getGson(true);
        }
    }

    public static String toJson(Object object) {
        return toJson(object, false);
    }

    public static String toJson(Object object, boolean serializeNulls) {
        try {
            String gsonString = null;
            if (serializeNulls) {
                if (gsonSerializeNulls != null) {
                    gsonString = gsonSerializeNulls.toJson(object);
                }
            } else {
                if (gson != null) {
                    gsonString = gson.toJson(object);
                }
            }
            return gsonString;
        } catch (Throwable throwable) {
            logger.error("序列化出错，尝试其他序列化：object={}", object);
            logger.error("序列化出错，尝试其他序列化：throwable={}", throwable);
            if (serializeNulls) {
                return JSON.toJSONString(object, SerializerFeature.WriteMapNullValue);
            } else {
                return JSON.toJSONString(object);
            }
        }
    }

    /**
     * 将gsonString转成泛型bean
     *
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T toObject(String json, Class<T> clazz) {
        try {
            T t = null;
            if (gson != null) {
                t = gson.fromJson(json, clazz);
            }
            return t;
        } catch (Throwable throwable) {
            logger.error("反序列化出错，尝试其他反序列化：json={}", json);
            logger.error("反序列化出错，尝试其他反序列化：throwable={}", throwable);
            return JSON.parseObject(json, clazz);
        }
    }

    /**
     * 将gsonString转成泛型bean
     *
     * @param json
     * @param type
     * @return
     */
    public static <T> T toObject(String json, Type type) {
        try {
            T t = null;
            if (gson != null) {
                t = gson.fromJson(json, type);
            }
            return t;
        } catch (Throwable throwable) {
            logger.error("反序列化出错，尝试其他反序列化：json={}", json);
            logger.error("反序列化出错，尝试其他反序列化：throwable={}", throwable);
            return JSON.parseObject(json, type);
        }
    }

    /**
     * 反序列化为数组
     *
     * @param json
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> List<T> toArray(String json, Class<T> tClass) {
        List<T> list = null;
        if (gson != null) {
            list = gson.fromJson(json, new TypeToken<List<T>>() {
            }.getType());
        }
        return list;
    }

    /**
     * 反序列化数组(可有效处理泛型问题)
     *
     * @param json
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonToList(String json, Class<T> tClass) {
        List<T> list = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (final JsonElement elem : array) {
            list.add(gson.fromJson(elem, tClass));
        }
        return list;
    }
}
