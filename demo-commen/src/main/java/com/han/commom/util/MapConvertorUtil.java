package com.han.commom.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * @Description TODO
 * @Date 2019/7/30 10:27
 * @Author hanyf
 */
public class MapConvertorUtil {

	private static final String BASIC_DATA_TYPE = "java.";
	private static final ObjectMapper objectMapper = new ObjectMapper();


	/**
	 * @Author hanyf
	 * @Description 对象转 map
	 * @Date 10:29 2019/7/30
	 * @Param [obj]
	 * @return java.util.Map<java.lang.Object,java.lang.Object>
	 **/
	public static Map<String, Object> objectAsMap(Object obj) throws IOException {
		Map map = illegal(obj) ? null : (Map) objectMapper.readValue(objectMapper.writeValueAsString(obj), Map.class);
		return map;
	}

	public static Map<String, Object> objectMapNoException(Object obj) {
		Map<String, Object> stringObjectMap = Maps.newHashMap();
		try {
			stringObjectMap = objectAsMap(obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stringObjectMap;
	}

	/**
	 * @Author hanyf
	 * @Description 对象转 String
	 * @Date 10:29 2019/7/30
	 * @Param [obj]
	 * @return java.lang.String
	 **/
	public static String objectAsString(Object obj) throws IOException {
		String string = objectMapper.writeValueAsString(obj);
		return string;
	}

	/**
	 * @Author hanyf
	 * @Description 对象转 Map 排除为空的字段
	 * @Date 10:30 2019/7/30
	 * @Param [obj]
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 **/
	public static Map<String, Object> objectAsMapExcludeNullValue(Object obj) throws IOException {
		if (obj == null) {
			return null;
		} else {
			Map<String, Object> map = (Map)objectMapper.readValue(objectMapper.writeValueAsString(obj), Map.class);
			removeNuLLValue(map);
			return map;
		}
	}

	/*
	 * @Author hanyf
	 * @Description 排除 Map 中为空的字段
	 * @Date 10:30 2019/7/30
	 * @Param [map]
	 * @return void
	 **/
	private static void removeNuLLValue(Map<String, Object> map) {
		if (map != null) {
			Iterator iterator = map.entrySet().iterator();

			while(iterator.hasNext()) {
				Map.Entry<Object, Object> next = (Map.Entry)iterator.next();
				Object value = next.getValue();
				if (value == null) {
					iterator.remove();
				}

				if (value instanceof Map) {
					removeNuLLValue((Map)value);
				}
			}

		}
	}


	private static boolean illegal(Object obj) {
		return null == obj || basicDataType(obj);
	}

	private static boolean basicDataType(Object obj) {
		return obj.getClass().getName().startsWith(BASIC_DATA_TYPE);
	}

}
