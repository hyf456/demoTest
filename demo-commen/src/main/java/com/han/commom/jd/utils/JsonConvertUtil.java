package com.han.commom.jd.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.util.List;

/**
 * Description: json转换类
 * Created by: yanghuai.
 * Created DateTime: 2017/5/12 13:09.
 * Project name: o2o-center.
 */
public class JsonConvertUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();


    public static String toStringFromObj(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("toStringFromObj=>obj=" + obj + " error:", e);
        }
    }


    public static <E> E toBean(String jsonStr, Class ob) {
        if (null != jsonStr && !"".equals(jsonStr)) {
            jsonStr = jsonStr.trim();
            if (jsonStr.startsWith("[")) {
                return (E) toList(jsonStr, ob);
            }
            return (E) toObject(jsonStr, ob);
        }
        return null;
    }

    private static <T> T toObject(String jsonStr, Class<T> clasObj) {
        try {
            return objectMapper.readValue(jsonStr, clasObj);
        } catch (Exception e) {
            throw new RuntimeException("toObject=>jsonStr=" + jsonStr + " error:", e);
        }
    }

    private static <T> List<T> toList(String jsonStr, Class<T> beanClass) {
        try {
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            return objectMapper.readValue(jsonStr, typeFactory.constructCollectionType(List.class, typeFactory.constructType(beanClass)));
        } catch (Exception e) {
            throw new RuntimeException("=toList=>jsontr=" + jsonStr + " error:", e);
        }
    }


}
