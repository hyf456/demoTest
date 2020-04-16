package com.han.spel;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 元对象
 *
 * @author: hanyf
 * @date: 2019-05-10 14:18
 */
@SuppressWarnings("unused")
public class MetaObject<T> {
    /**
     * 缓存
     */
    //private static final ConcurrentHashMap<String, Map<String, Field>> CLASS_CONCURRENT_HASH_MAP = new ConcurrentHashMap<>(20);
    private static final ConcurrentHashMap<Class, Map<String, Field>> CLASS_CONCURRENT_HASH_MAP = new ConcurrentHashMap<>(20);
    private static ExpressionParser expressionParser = new SpelExpressionParser();
    private Object _lock = new Object();
    private T target;
    private Map<String, Field> fieldMap;
    private Map<String, Expression> expressionMap = new ConcurrentHashMap<>(5);
    private StandardEvaluationContext evaluationContext = new StandardEvaluationContext();

    /**
     * 初始化元对象
     *
     * @param target 目标对象
     */
    public MetaObject(T target) {
        this.target = target;
        if (target == null) {
            throw new NullPointerException("target不能为空");
        }
        evaluationContext.setRootObject(target);
        fieldMap = getDeclaredField(target.getClass());
    }

    /**
     * 获取目标对象
     *
     * @return
     */
    public T getTarget() {
        return target;
    }

    /**
     * 获取字段列表
     *
     * @return
     */
    public List<String> getFieldList() {
        return fieldMap.keySet().stream().collect(Collectors.toList());
    }

    /**
     * 是否包含指定字段
     *
     * @param field
     * @return
     */
    public boolean containsField(String field) {
        return fieldMap.containsKey(field);
    }

    /**
     * 获取字段值
     *
     * @param field 要获取值的字段名
     * @return 字段值
     */
    public Object getFieldValue(String field) {
        if (!containsField(field)) {
            // 尝试Spel表达式获取
            return getValue(field);
        }
        try {
            Field fieldItem = fieldMap.get(field);
            fieldItem.setAccessible(true);
            return fieldItem.get(target);
        } catch (Throwable throwable) {
            // 尝试Spel表达式获取
            return getValue(field);
        }
    }

    /**
     * 根据Spel表达式获取值
     *
     * @param spel Spel表达式
     * @return 值
     */
    public Object getValue(String spel) {
        checkSpel(spel);
        Expression expression = expressionMap.get(spel);
        return expression.getValue(evaluationContext);
    }

    /**
     * 设置字段值
     *
     * @param field 要设置值的字段名
     * @param value 要设置的值
     * @return 当前对象
     */
    public MetaObject<T> setFieldValue(String field, Object value) {
        if (!containsField(field)) {
            // 尝试Spel表达式设置值
            setValue(field, value);
        }
        try {
            Field fieldItem = fieldMap.get(field);
            fieldItem.setAccessible(true);
            fieldItem.set(target, value);
        } catch (Throwable throwable) {
            // 尝试Spel表达式设置值
            setValue(field, value);
        }
        return this;
    }

    /**
     * 按照Spel表达式设置字段值
     *
     * @param spel  Spel表达式
     * @param value 字段值
     * @return 当前对象
     */
    public MetaObject<T> setValue(String spel, Object value) {
        checkSpel(spel);
        Expression expression = expressionMap.get(spel);
        expression.setValue(evaluationContext, value);
        return this;
    }

    /**
     * 获取类的所有字段
     *
     * @param clazz 要获取字段的类
     * @return
     */
    public static Map<String, Field> getDeclaredField(Class clazz) {
        Map<String, Field> fieldMap = getDeclaredField(clazz, null);
        return fieldMap;
    }

    /**
     * 检查Spel表达式并存入缓存
     *
     * @param spel Spel表达式
     */
    private void checkSpel(String spel) {
        if (!StringUtils.hasText(spel)) {
            throw new IllegalArgumentException("spel不能为空");
        }
        if (!expressionMap.containsKey(spel)) {
            synchronized (_lock) {
                if (!expressionMap.containsKey(spel)) {
                    Expression expression = expressionParser.parseExpression(spel);
                    expressionMap.putIfAbsent(spel, expression);
                }
            }
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        evaluationContext = null;
        expressionMap.clear();
        expressionMap = null;
        fieldMap.clear();
        fieldMap = null;
    }

    /**
     * 获取类的所有字段
     *
     * @param clazz    要获取字段的类
     * @param fieldMap 字段Map
     * @return
     */
    public static Map<String, Field> getDeclaredField(Class clazz, Map<String, Field> fieldMap) {
        if (fieldMap == null) {
            fieldMap = new HashMap<>();
        }
        if (clazz != null) {
            if (CLASS_CONCURRENT_HASH_MAP.containsKey(clazz)) {
                fieldMap = CLASS_CONCURRENT_HASH_MAP.get(clazz);
            }
            if (CollectionUtils.isEmpty(fieldMap)) {
                synchronized (clazz) {
                    Map<String, Field> currentFieldMap = new HashMap<>();
                    Field[] objectDeclaredField = clazz.getDeclaredFields();
                    for (Field item : objectDeclaredField) {
                        String name = item.getName();
                        if (!currentFieldMap.containsKey(name)) {
                            currentFieldMap.put(name, item);
                        }
                    }
                    Map<String, Field> superFieldMap = getDeclaredField(clazz.getSuperclass(), fieldMap);
                    if (superFieldMap != null && superFieldMap.size() > 0) {
                        currentFieldMap.putAll(superFieldMap);
                    }
                    if (currentFieldMap != null && currentFieldMap.size() > 0) {
                        fieldMap.putAll(currentFieldMap);
                    }
                    CLASS_CONCURRENT_HASH_MAP.put(clazz, currentFieldMap);
                }
            }
        }
        return fieldMap;
    }
}
