package com.han.commom.jd.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;

/**
 * @ClassName JacksonUtil
 * @Description JSON 序列化
 * @Author hanyunfei1
 * @Date 2021/4/26 15:09
 * @Version 1.0
 **/
public class JacksonUtil {

	private final static ObjectMapper mapper = new ObjectMapper()
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
			.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

	public static String objectToJson(Object object) {
		if (object == null) {
			return null;
		}
		try {
			return object instanceof String ? (String) object : mapper.writeValueAsString(object);
		} catch (Exception e) {
			throw new RuntimeException("Parse Object to Json error, the object: " + object.toString(), e);
		}
	}


	public static <T> T jsonToObject(String josnStr, Class<T> clazz) {
		if (josnStr == null || "".equals(josnStr) || clazz == null) {
			return null;
		}
		try {
			return clazz.equals(String.class) ? (T) josnStr : mapper.readValue(josnStr, clazz);
		} catch (Exception e) {
			throw new RuntimeException("Parse Json to Object error, the Json: " + josnStr, e);
		}
	}
}
