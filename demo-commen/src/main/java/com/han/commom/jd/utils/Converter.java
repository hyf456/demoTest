package com.han.commom.jd.utils;

import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @ClassName CalculateUtils
 * @Description 集合转换 Utils
 * @Author hanyunfei1
 * @Date 2021/5/4 14:03
 * @Version 1.0
 **/
public class Converter {

    /**
     * 集合转换
     *
     * @param srcList
     * @param function
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> List<R> getList(List<T> srcList, Function<T, R> function) {
        if (CollectionUtils.isNotEmpty(srcList)) {
            return srcList.stream().map(function).collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }
}
