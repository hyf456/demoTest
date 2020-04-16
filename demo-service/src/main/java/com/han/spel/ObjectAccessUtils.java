package com.han.spel;

import com.google.gson.*;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 对象访问工具类
 * <br>
 * 支持的路径格式参考：
 * <p>
 * address.id
 * <br>
 * detailList[0].name
 * <br>
 * payInfo.payMethodList[0].name
 * <br>
 * 0.text
 * <br>
 * data.address.city.text
 * <br>
 * ?0.text
 * <br>
 * detailList[?0].name
 * <br>
 * data.address?.city?.text
 * </p>
 *
 * @author: hanyf
 * @date: 2019-06-11 10:38
 */
public class ObjectAccessUtils {
    private static JsonParser jsonParser = new JsonParser();
    private static ExpressionParser expressionParser = new SpelExpressionParser();

    /**
     * 以字段路径形式访问对象
     * <br>
     * 字段路径支持所有常见访问场景。比如：<br>
     * address.id <br>
     * detailList[0].name <br>
     * payInfo.payMethodList[0].name <br>
     * 0.text <br>
     * data.address.city.text <br>
     * ?0.text <br>
     * detailList[?2].name <br>
     * data.address?.city?.text <br>
     *
     * @param target    要访问的目标对象
     * @param fieldPath 字段路径
     * @param clazz     要返回的类型的类
     * @param <T>       要返回的类型
     * @return 值
     * @throws IllegalAccessException 当无法访问对象或者字段时触发
     */
    public static <T> T get(Object target, String fieldPath, Class<T> clazz) throws IllegalAccessException {
        return get(target, fieldPath, clazz, "");
    }

    /**
     * 以字段路径形式访问对象
     * <br>
     * 字段路径支持所有常见访问场景。比如：<br>
     * address.id <br>
     * detailList[0].name <br>
     * payInfo.payMethodList[0].name <br>
     * 0.text <br>
     * data.address.city.text <br>
     * ?0.text <br>
     * detailList[?2].name <br>
     * data.address?.city?.text <br>
     *
     * @param target    要访问的目标对象
     * @param fieldPath 字段路径
     * @param clazz     要返回的类型的类
     * @param delimiter 分隔符
     * @param <T>       要返回的类型
     * @return 值
     * @throws IllegalAccessException 当无法访问对象或者字段时触发
     */
    public static <T> T get(Object target, String fieldPath, Class<T> clazz, String delimiter) throws IllegalAccessException {
        return get(target, fieldPath, clazz, delimiter, null);
    }

    /**
     * 以字段路径形式访问对象
     * <br>
     * 字段路径支持所有常见访问场景。比如：<br>
     * address.id <br>
     * detailList[0].name <br>
     * payInfo.payMethodList[0].name <br>
     * 0.text <br>
     * data.address.city.text <br>
     * ?0.text <br>
     * detailList[?2].name <br>
     * data.address?.city?.text <br>
     *
     * @param target    要访问的目标对象
     * @param fieldPath 字段路径
     * @param clazz     要返回的类型的类
     * @param delimiter 分隔符
     * @param nullText  null时替代文本
     * @param <T>       要返回的类型
     * @return 值
     * @throws IllegalAccessException 当无法访问对象或者字段时触发
     */
    public static <T> T get(Object target, String fieldPath, Class<T> clazz, String delimiter, String nullText) throws IllegalAccessException {
        if (target == null) {
            throw new IllegalArgumentException("要访问的目标对象不能为空");
        } else if (fieldPath == null) {
            throw new IllegalArgumentException("要访问的目标对象的字段路径不能为空");
        } else if (!StringUtils.hasText(fieldPath)) {
            throw new IllegalArgumentException("要访问的目标对象的字段路径不能为空");
        }
        fieldPath = fieldPath.replaceAll(",", "+");
        if (fieldPath.contains("+")) {
            if (!String.class.equals(clazz)) {
                throw new IllegalArgumentException("当字段路径中包含+时，clazz只能是String.class");
            }
            String[] fieldPathList = fieldPath.split("\\+");
            ArrayList<String> results = new ArrayList<>();
            for (String fp : fieldPathList) {
                if (StringUtils.hasText(fp)) {
                    String item = get(target, fp, String.class);
                    if (item == null) {
                        item = nullText;
                    }
                    results.add(item);
                }
            }
            return (T) String.join(delimiter, results);
        }

        if (fieldPath.startsWith("*")) {
            // fieldPath = fieldPath.substring(1);
            throw new IllegalArgumentException("路径不能以*开头");
        }
        JsonElement targetElement;
        if (target instanceof JsonElement) {
            targetElement = (JsonElement) target;
        } else {
            String jsonText = GsonUtils.toJson(target);
            targetElement = jsonParser.parse(jsonText);
        }
        if (targetElement == null) {
            throw new IllegalArgumentException("无法以json形式访问目标对象");
        }
        FieldAccessDescriptor fieldAccessDescriptor = FieldAccessDescriptor.parse(fieldPath);
        JsonElement valueElement = getValue(targetElement, fieldAccessDescriptor);
        if (clazz == JsonElement.class) {
            return (T) valueElement;
        } else {
            return valueElement.isJsonNull() ? null : GsonUtils.toObject(GsonUtils.toJson(valueElement), clazz);
        }
    }

    /**
     * 获取值
     *
     * @param targetElement         目标元素
     * @param fieldAccessDescriptor 访问标识符
     * @return 值
     * @throws IllegalAccessException 访问出错时抛出
     */
    private static JsonElement getValue(JsonElement targetElement, FieldAccessDescriptor fieldAccessDescriptor) throws IllegalAccessException {
        JsonElement valueElement;
        boolean needBreak = false;
        if (fieldAccessDescriptor.accessArray()) {
            JsonArray jsonArray = targetElement.getAsJsonArray();
            if (fieldAccessDescriptor.getCurrentIndex() < 0 || jsonArray.size() <= fieldAccessDescriptor.getCurrentIndex()) {
                if (fieldAccessDescriptor.isOptionalAccess()) {
                    valueElement = JsonNull.INSTANCE;
                    needBreak = true;
                } else {
                    throw new IndexOutOfBoundsException("索引超界：" + fieldAccessDescriptor.getCurrentPath());
                }
            } else {
                valueElement = jsonArray.get(fieldAccessDescriptor.getCurrentIndex());
            }
        } else {
            if (targetElement.isJsonNull()) {
                if (fieldAccessDescriptor.isOptionalAccess()) {
                    valueElement = JsonNull.INSTANCE;
                    needBreak = true;
                } else {
                    throw new IllegalAccessException("无法访问对象" + fieldAccessDescriptor.getCurrentPath());
                }
            } else {
                JsonObject jsonObject = targetElement.getAsJsonObject();
                if (!jsonObject.has(fieldAccessDescriptor.getCurrentField())) {
                    if (fieldAccessDescriptor.isOptionalAccess()) {
                        valueElement = JsonNull.INSTANCE;
                        needBreak = true;
                    } else {
                        throw new IllegalAccessException("无法访问对象" + fieldAccessDescriptor.getCurrentPath());
                    }
                } else {
                    valueElement = jsonObject.get(fieldAccessDescriptor.getCurrentField());
                }
            }
        }
        if (!needBreak && fieldAccessDescriptor.hasNext()) {
            valueElement = getValue(valueElement, fieldAccessDescriptor.getNextFieldAccessDescriptor());
        }
        return valueElement;
    }

    /**
     * 计算值
     *
     * @param target   目标
     * @param spel     表达式
     * @param thenSpel 后续表达式集合
     * @return 值
     */
    public static Object get(Object target, String spel, String[] thenSpel) throws IllegalAccessException {
        if (thenSpel != null && thenSpel.length > 0) {
            return get(target, spel, Arrays.asList(thenSpel));
        }
        return get(target, spel, Arrays.asList());
    }

    /**
     * 计算值
     *
     * @param target   目标
     * @param spel     表达式
     * @param thenSpel 后续表达式集合
     * @return 值
     */
    public static Object get(Object target, String spel, Collection<String> thenSpel) throws IllegalAccessException {
        Object result = null;
        if (target != null) {
            if (StringUtils.hasText(spel)) {
                Expression expression = expressionParser.parseExpression(spel);
                if (target instanceof ProceedingJoinPoint) {
                    ProceedingJoinPoint pointcut = (ProceedingJoinPoint) target;
                    CacheKeyBuilder.SpelInfo spelInfo = CacheKeyBuilder.getSpel(pointcut, spel);
                    result = spelInfo.getObject(pointcut.getArgs());
                } else {
                    EvaluationContext context = new StandardEvaluationContext(target);
                    result = expression.getValue(context);
                }
            } else {
                result = target;
            }
            if (result != null && thenSpel != null && !thenSpel.isEmpty()) {
                // 需要后续计算
                if (!Collection.class.isAssignableFrom(result.getClass())) {
                    throw new IllegalAccessException("前置计算返回集合才能执行后续计算");
                }
                List<String> spelList = thenSpel.stream().collect(Collectors.toList());
                List<String> subSpelList = spelList.size() > 1 ? spelList.subList(1, spelList.size() - 1) : new ArrayList<>();
                Collection collection = (Collection) result;
                List<Object> resultList = new ArrayList<>();
                for (Object item : collection) {
                    if (item != null) {
                        Object itemResult = get(item, spelList.get(0), subSpelList);
                        if (itemResult != null) {
                            if (Collection.class.isAssignableFrom(itemResult.getClass())) {
                                ((Collection) itemResult).stream().filter(d -> d != null).forEach(cItem -> resultList.add(cItem));
                            } else {
                                resultList.add(itemResult);
                            }
                        }
                    }
                }
                result = resultList;
            }
        }
        return result;
    }
}
