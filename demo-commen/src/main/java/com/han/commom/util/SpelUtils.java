package com.han.commom.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description SpEL表达式工具类，用于通过SpEL表达式获取对象中的信息
 * @ClassName SpelUtils
 * @Author hanyunfei1
 * @Date 2025/8/26 21:10
 * @Version 1.0
 **/
@Slf4j
public class SpelUtils {

    /**
     * SpEL表达式解析器
     */
    private static final ExpressionParser PARSER = new SpelExpressionParser();

    /**
     * 表达式缓存
     */
    private static final Map<String, Expression> EXPRESSION_CACHE = new ConcurrentHashMap<>();

    /**
     * 模板解析上下文，用于解析模板中的表达式，如：#{expression}
     */
    private static final ParserContext TEMPLATE_PARSER_CONTEXT = new TemplateParserContext("#{", "}");

    /**
     * 获取表达式
     *
     * @param expressionString 表达式字符串
     * @return 表达式对象
     */
    private static Expression getExpression(String expressionString) {
        if (StringUtils.isEmpty(expressionString)) {
            throw new IllegalArgumentException("Expression string cannot be empty");
        }
        return EXPRESSION_CACHE.computeIfAbsent(expressionString, PARSER::parseExpression);
    }

    /**
     * 获取模板表达式
     *
     * @param expressionString 表达式字符串
     * @return 表达式对象
     */
    private static Expression getTemplateExpression(String expressionString) {
        if (StringUtils.isEmpty(expressionString)) {
            throw new IllegalArgumentException("Expression string cannot be empty");
        }
        String cacheKey = "template:" + expressionString;
        return EXPRESSION_CACHE.computeIfAbsent(cacheKey, key -> PARSER.parseExpression(expressionString, TEMPLATE_PARSER_CONTEXT));
    }

    /**
     * 创建评估上下文
     *
     * @param rootObject 根对象
     * @return 评估上下文
     */
    private static EvaluationContext createEvaluationContext(Object rootObject) {
        return new StandardEvaluationContext(rootObject);
    }

    /**
     * 解析表达式
     *
     * @param rootObject       根对象
     * @param expressionString 表达式字符串
     * @param resultType       结果类型
     * @param <T>              结果泛型
     * @return 表达式解析结果
     */
    @SuppressWarnings("swallow-exception: 解析表达式失败,不处理异常")
    public static <T> T getValue(Object rootObject, String expressionString, Class<T> resultType) {
        try {
            Expression expression = getExpression(expressionString);
            EvaluationContext context = createEvaluationContext(rootObject);
            return expression.getValue(context, resultType);
        } catch (Exception e) {
            log.error("Failed to get value from expression: {}, rootObject: {}", expressionString, rootObject, e);
            return null;
        }
    }

    /**
     * 解析表达式，返回Object类型
     *
     * @param expressionString 表达式字符串
     * @param rootObject       根对象
     * @return 表达式解析结果
     */
    public static Object getValue(Object rootObject, String expressionString) {
        return getValue(rootObject, expressionString, Object.class);
    }

    /**
     * 解析模板表达式
     *
     * @param expressionString 表达式字符串，如：Hello #{name}
     * @param rootObject       根对象
     * @param resultType       结果类型
     * @param <T>              结果泛型
     * @return 表达式解析结果
     */
    @SuppressWarnings("swallow-exception: 解析模版失败,不处理异常")
    public static <T> T getValueFromTemplate(Object rootObject, String expressionString, Class<T> resultType) {
        try {
            Expression expression = getTemplateExpression(expressionString);
            EvaluationContext context = createEvaluationContext(rootObject);
            return expression.getValue(context, resultType);
        } catch (Exception e) {
            log.error("Failed to get value from template expression: {}, rootObject: {}", expressionString, rootObject, e);
            return null;
        }
    }

    /**
     * 解析模板表达式，返回String类型
     *
     * @param rootObject       根对象
     * @param expressionString 表达式字符串，如：Hello #{name}
     * @return 表达式解析结果
     */
    public static String getValueFromTemplate(Object rootObject, String expressionString) {
        return getValueFromTemplate(rootObject, expressionString, String.class);
    }

    /**
     * 解析模板表达式，返回Integer类型
     *
     * @param rootObject       根对象
     * @param expressionString 表达式字符串，如：Hello #{name}
     * @return 表达式解析结果
     */
    public static Integer getIntegerFromTemplate(Object rootObject, String expressionString) {
        return getValueFromTemplate(rootObject, expressionString, Integer.class);
    }

    /**
     * 解析模板表达式，返回Boolean类型
     *
     * @param rootObject       根对象
     * @param expressionString 表达式字符串，如：Hello #{name}
     * @return 表达式解析结果
     */
    public static Boolean getBooleanFromTemplate(Object rootObject, String expressionString) {
        return getValueFromTemplate(rootObject, expressionString, Boolean.class);
    }

    /**
     * 设置对象属性值
     *
     * @param rootObject       根对象
     * @param expressionString 表达式字符串
     * @param value            要设置的值
     */
    @SuppressWarnings("swallow-exception: 设置属性失败,不处理异常")
    public static void setValue(Object rootObject, String expressionString, Object value) {
        try {
            Expression expression = getExpression(expressionString);
            EvaluationContext context = createEvaluationContext(rootObject);
            expression.setValue(context, value);
        } catch (Exception e) {
            log.error("Failed to set value for expression: {}, rootObject: {}, value: {}", expressionString, rootObject, value, e);
        }
    }

    /**
     * 解析布尔表达式
     *
     * @param rootObject       根对象
     * @param expressionString 表达式字符串
     * @return 布尔结果
     */
    public static boolean evaluateBoolean(Object rootObject, String expressionString) {
        Boolean result = getValue(rootObject, expressionString, Boolean.class);
        return result != null && result;
    }

    /**
     * 解析表达式，返回指定类型的结果
     *
     * @param rootObject       根对象
     * @param expressionString 表达式字符串
     * @param variables        变量映射
     * @param resultType       结果类型
     * @param <T>              结果泛型
     * @return 表达式解析结果
     */
    @SuppressWarnings("swallow-exception: 解析获取数据失败,不处理异常")
    public static <T> T getValue(Object rootObject, String expressionString, Map<String, Object> variables, Class<T> resultType) {
        try {
            Expression expression = getExpression(expressionString);
            StandardEvaluationContext context = new StandardEvaluationContext(rootObject);
            if (variables != null && !variables.isEmpty()) {
                variables.forEach(context::setVariable);
            }
            return expression.getValue(context, resultType);
        } catch (Exception e) {
            log.error("Failed to get value from expression: {}, rootObject: {}, variables: {}", expressionString, rootObject, variables, e);
            return null;
        }
    }

    /**
     * 解析表达式，返回Object类型
     *
     * @param rootObject       根对象
     * @param expressionString 表达式字符串
     * @param variables        变量映射
     * @return 表达式解析结果
     */
    public static Object getValue(Object rootObject, String expressionString, Map<String, Object> variables) {
        return getValue(rootObject, expressionString, variables, Object.class);
    }

    /**
     * 解析模板表达式
     *
     * @param rootObject       根对象
     * @param expressionString 表达式字符串，如：Hello #{name}
     * @param variables        变量映射
     * @param resultType       结果类型
     * @param <T>              结果泛型
     * @return 表达式解析结果
     */
    @SuppressWarnings("swallow-exception: 解析模版失败,不处理异常")
    public static <T> T getValueFromTemplate(Object rootObject, String expressionString, Map<String, Object> variables, Class<T> resultType) {
        try {
            Expression expression = getTemplateExpression(expressionString);
            StandardEvaluationContext context = new StandardEvaluationContext(rootObject);
            if (variables != null && !variables.isEmpty()) {
                variables.forEach(context::setVariable);
            }
            return expression.getValue(context, resultType);
        } catch (Exception e) {
            log.error("Failed to get value from template expression: {}, rootObject: {}, variables: {}", expressionString, rootObject, variables, e);
            return null;
        }
    }

    /**
     * 解析模板表达式，返回String类型
     *
     * @param rootObject       根对象
     * @param expressionString 表达式字符串，如：Hello #{name}
     * @param variables        变量映射
     * @return 表达式解析结果
     */
    public static String getValueFromTemplate(Object rootObject, String expressionString, Map<String, Object> variables) {
        return getValueFromTemplate(rootObject, expressionString, variables, String.class);
    }
}