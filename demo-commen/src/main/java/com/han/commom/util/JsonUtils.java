package com.han.commom.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @ClassName JsonUtils
 * @Description ObjectMapper序列化
 * @Author hanyunfei1
 * @Date 2021/5/13 18:49
 * @Version 1.0
 **/
@Slf4j
public class JsonUtils {

	// 加载速度太慢了，放在静态代码块中
	// private static final ObjectMapper mapper = new ObjectMapper();
	private static ObjectMapper mapper;

	/**
	 * 设置一些通用的属性
	 */
	static {
		mapper = new ObjectMapper();
		// 如果json中有新增的字段并且是实体类类中不存在的，不报错
		// mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
		// 如果存在未知属性，则忽略不报错
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// 允许key没有双引号
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		// 允许key有单引号
		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		// 允许整数以0开头
		mapper.configure(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS, true);
		// 允许字符串中存在回车换行控制符
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		/**
		 * 通过该方法对mapper对象进行设置，所有序列化的对象都将按改规则进行系列化
		 * Include.Include.ALWAYS 默认
		 * Include.NON_DEFAULT 属性为默认值不序列化
		 * Include.NON_EMPTY 属性为 空（“”） 或者为 NULL 都不序列化
		 * Include.NON_NULL 属性为NULL 不序列化
		 */
		// 属性为空不序列化
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	}

	public static String toJSONString(Object obj) {
		return obj != null ? toJSONString(obj, () -> "", false) : "";
	}

	public static String toFormatJSONString(Object obj) {
		return obj != null ? toJSONString(obj, () -> "", true) : "";
	}


	public static String toJSONString(Object obj, Supplier<String> defaultSupplier, boolean format) {
		try {
			if (obj == null) {
				return defaultSupplier.get();
			}
			if (obj instanceof String) {
				return obj.toString();
			}
			if (obj instanceof Number) {
				return obj.toString();
			}
			if (format) {
				return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
			}
			return mapper.writeValueAsString(obj);
		} catch (Throwable e) {
			log.error(String.format("toJSONString %s", obj != null ? obj.toString() : "null"), e);
		}
		return defaultSupplier.get();
	}

	public static <T> T toJavaObject(String value, Class<T> tClass) {
		return StringUtils.isNotBlank(value) ? toJavaObject(value, tClass, () -> null) : null;
	}

	public static <T> T toJavaObject(Object obj, Class<T> tClass) {
		return obj != null ? toJavaObject(toJSONString(obj), tClass, () -> null) : null;
	}

	public static <T> T toJavaObject(String value, Class<T> tClass, Supplier<T> defaultSupplier) {
		try {
			if (StringUtils.isBlank(value)) {
				return defaultSupplier.get();
			}
			return mapper.readValue(value, tClass);
		} catch (Throwable e) {
			log.error(String.format("toJavaObject exception: \n %s\n %s", value, tClass), e);
		}
		return defaultSupplier.get();
	}

	public static <T> List<T> toJavaObjectList(String value, Class<T> tClass) {
		return StringUtils.isNotBlank(value) ? toJavaObjectList(value, tClass, () -> null) : null;
	}

	public static <T> List<T> toJavaObjectList(Object obj, Class<T> tClass) {
		return obj != null ? toJavaObjectList(toJSONString(obj), tClass, () -> null) : null;
	}

	public static <T> List<T> toJavaObjectList(String value, Class<T> tClass, Supplier<List<T>> defaultSupplier) {
		try {
			if (StringUtils.isBlank(value)) {
				return defaultSupplier.get();
			}
			JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, tClass);
			return mapper.readValue(value, javaType);
		} catch (Throwable e) {
			log.error(String.format("toJavaObjectList exception \n%s\n%s", value, tClass), e);
		}
		return defaultSupplier.get();
	}

	// 简单地直接用json复制或者转换(Cloneable)
	public static <T> T jsonCopy(Object obj, Class<T> tClass) {
		return obj != null ? toJavaObject(toJSONString(obj), tClass) : null;
	}

	public static Map<String, Object> toMap(String value) {
		return StringUtils.isNotBlank(value) ? toMap(value, () -> null) : null;
	}

	public static Map<String, Object> toMap(Object value) {
		return value != null ? toMap(value, () -> null) : null;
	}

	public static Map<String, Object> toMap(Object value, Supplier<Map<String, Object>> defaultSupplier) {
		if (value == null) {
			return defaultSupplier.get();
		}
		try {
			if (value instanceof Map) {
				return (Map<String, Object>) value;
			}
		} catch (Exception e) {
			log.info("fail to convert" + toJSONString(value), e);
		}
		return toMap(toJSONString(value), defaultSupplier);
	}

	public static Map<String, Object> toMap(String value, Supplier<Map<String, Object>> defaultSupplier) {
		if (StringUtils.isBlank(value)) {
			return defaultSupplier.get();
		}
		try {
			return toJavaObject(value, LinkedHashMap.class);
		} catch (Exception e) {
			log.error(String.format("toMap exception\n%s", value), e);
		}
		return defaultSupplier.get();
	}


	public static List<Object> toList(String value) {
		return StringUtils.isNotBlank(value) ? toList(value, () -> null) : null;
	}

	public static List<Object> toList(Object value) {
		return value != null ? toList(value, () -> null) : null;
	}

	public static List<Object> toList(String value, Supplier<List<Object>> defaultSuppler) {
		if (StringUtils.isBlank(value)) {
			return defaultSuppler.get();
		}
		try {
			return toJavaObject(value, List.class);
		} catch (Exception e) {
			log.error("toList exception\n" + value, e);
		}
		return defaultSuppler.get();
	}

	public static List<Object> toList(Object value, Supplier<List<Object>> defaultSuppler) {
		if (value == null) {
			return defaultSuppler.get();
		}
		if (value instanceof List) {
			return (List<Object>) value;
		}
		return toList(toJSONString(value), defaultSuppler);
	}

	public static long getLong(Map<String, Object> map, String key) {
		if (MapUtils.isEmpty(map)) {
			return 0L;
		}
		String valueStr = String.valueOf(map.get(key));
		if (StringUtils.isBlank(valueStr) || !StringUtils.isNumeric(valueStr)) {
			return 0L;
		}
		return Long.valueOf(valueStr);
	}

	public static int getInt(Map<String, Object> map, String key) {
		if (MapUtils.isEmpty(map)) {
			return 0;
		}
		String valueStr = String.valueOf(map.get(key));
		if (StringUtils.isBlank(valueStr) || !StringUtils.isNumeric(valueStr)) {
			return 0;
		}
		return Integer.valueOf(valueStr);
	}
}

