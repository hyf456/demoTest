package com.han.spel;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存键生成工具
 *
 * @author: hanyf
 * @date: 2019-06-26 10:47
 */
public class CacheKeyBuilder {
    private static ExpressionParser expressionParser = new SpelExpressionParser();
    private static ConcurrentHashMap<String, SpelInfo> expressionMap = new ConcurrentHashMap<>();
    public static final String COMMON_PRE = "yestae-cache:";
    public static final String NULL_KEY_TEXT = "<NULL>";

    public static String trimCacheKeywords(String key) {
        if (StringUtils.hasText(key)) {
            if (key.startsWith(CacheKeyBuilder.COMMON_PRE) && key.length() > CacheKeyBuilder.COMMON_PRE.length()) {
                key = key.substring(CacheKeyBuilder.COMMON_PRE.length());
            }
        }
        return key;
    }

    /**
     * 生成key
     *
     * @param key    键
     * @param params 参数
     * @param spel   SpEL表达式
     * @param args   参数值
     * @return
     * @throws IllegalAccessException 当访问异常时抛出
     */
    public static String generate(ProceedingJoinPoint proceedingJoinPoint, String key, String params, String spel, Object[] args) throws IllegalAccessException {
        return generate(proceedingJoinPoint, key, params, spel, args, NULL_KEY_TEXT);
    }

    /**
     * 生成key
     *
     * @param key      键
     * @param params   参数
     * @param spel     SpEL表达式
     * @param args     参数值
     * @param nullText null替代文本
     * @return
     * @throws IllegalAccessException 当访问异常时抛出
     */
    public static String generate(ProceedingJoinPoint proceedingJoinPoint, String key, String params, String spel, Object[] args, String nullText) throws IllegalAccessException {
        StringBuilder keyBuilder = new StringBuilder("yestae-cache:");
        if (StringUtils.hasText(key)) {
            keyBuilder.append(key);
        }
        if (StringUtils.hasText(params)) {
            String paramsResult = ObjectAccessUtils.get(args, params, String.class, "_", nullText);
            keyBuilder.append(":");
            keyBuilder.append(paramsResult);
        } else if (StringUtils.hasText(spel)) {
            keyBuilder.append(":");
            String spelResult = getSpel(proceedingJoinPoint, spel).getValue(args);
            keyBuilder.append(spelResult);
        }
        return keyBuilder.toString();
    }

    /**
     * 获取表达式计算信息
     *
     * @param proceedingJoinPoint 切点
     * @param spel                SpEL表达式
     * @return 表达式计算信息
     */
    public static SpelInfo getSpel(ProceedingJoinPoint proceedingJoinPoint, String spel) {
        String id = String.format("%s_on_%s", spel, proceedingJoinPoint.toLongString());
        if (!expressionMap.containsKey(id)) {
            synchronized (expressionMap) {
                if (!expressionMap.containsKey(id)) {
                    SpelInfo spelInfo = generateSpelInfo(proceedingJoinPoint, spel);
                    expressionMap.putIfAbsent(id, spelInfo);
                }
            }
        }
        return expressionMap.get(id);
    }

    /**
     * 生成表达式计算信息
     *
     * @param proceedingJoinPoint 切点
     * @param spel                SpEL表达式
     * @return 表达式计算信息
     */
    private static SpelInfo generateSpelInfo(ProceedingJoinPoint proceedingJoinPoint, String spel) {
        // 构建Expression
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Expression expression = expressionParser.parseExpression(spel);
        SpelInfo spelInfo = new SpelInfo();
        spelInfo.setMethod(method);
        spelInfo.setExpression(expression);
        return spelInfo;
    }

    /**
     * 表达式计算信息
     * <p>
     * 1、EvaluationContext暂时无法缓存
     * 2、涉及到多线程下变量的改动
     * </p>
     */
    @SuppressWarnings("unused")
    public static class SpelInfo {
        /**
         * 方法
         */
        private Method method;
        /**
         * 计算上下文
         */
        private EvaluationContext evaluationContext;
        /**
         * 表达式
         */
        private Expression expression;

        public Method getMethod() {
            return method;
        }

        public void setMethod(Method method) {
            this.method = method;
        }

        public EvaluationContext getEvaluationContext() {
            return evaluationContext;
        }

        public void setEvaluationContext(EvaluationContext evaluationContext) {
            this.evaluationContext = evaluationContext;
        }

        public Expression getExpression() {
            return expression;
        }

        public void setExpression(Expression expression) {
            this.expression = expression;
        }

        /**
         * 获取值
         *
         * @param args 参数
         * @return 结果
         */
        public String getValue(Object... args) {
            EvaluationContext evaluationContext = createEvaluationContext(args, args);
            return getExpression().getValue(evaluationContext, String.class);
        }

        public Object getObject(Object... args) {
            EvaluationContext evaluationContext = createEvaluationContext(args, args);
            return getExpression().getValue(evaluationContext, Object.class);
        }

        /**
         * 获取布尔值
         *
         * @param args 参数
         * @return 布尔值
         */
        public boolean getBooleanValue(Object... args) {
            EvaluationContext evaluationContext = createEvaluationContext(args, args);
            return getExpression().getValue(evaluationContext, Boolean.class);
        }

        /**
         * 获取值
         *
         * @param args 参数
         * @return 结果
         */
        public String getValue(Object root, Object... args) {
            EvaluationContext evaluationContext = createEvaluationContext(root, args);
            return getExpression().getValue(evaluationContext, root, String.class);
        }

        /**
         * 获取布尔值
         *
         * @param args 参数
         * @return 布尔值
         */
        public boolean getBooleanValue(Object root, Object... args) {
            EvaluationContext evaluationContext = createEvaluationContext(root, args);
            return getExpression().getValue(evaluationContext, root, Boolean.class);
        }

        /**
         * 创建计算上下文
         *
         * @param root 根对象
         * @param args 参数
         * @return 计算上下文
         */
        private EvaluationContext createEvaluationContext(Object root, Object[] args) {
            EvaluationContext evaluationContext = new MethodBasedEvaluationContext(root, method, args, new DefaultParameterNameDiscoverer());
            // 预热变量，以便后续操作能覆盖变量
            evaluationContext.lookupVariable("p0");
            if (args != null && args.length > 0) {
                for (int i = 0; i < args.length; i++) {
                    evaluationContext.setVariable("a" + i, args[i]);
                    evaluationContext.setVariable("p" + i, args[i]);
                }
            }
            evaluationContext.setVariable("result", root);
            return evaluationContext;
        }
    }
}
