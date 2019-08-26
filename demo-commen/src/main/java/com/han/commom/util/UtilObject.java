package com.han.commom.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Description 对象JSON转换
 * @Date 2019/8/12 15:38
 * @Author hanyf
 */
public class UtilObject {

	private static ObjectMapper mapper = new ObjectMapper();

	public static String objectToString(Object object){
		String s = null;
		try {
			s = mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return s;
	}

	public static Object stringToObject(String s, Class<?> cla){
		Object o = null;
		try {
			o = mapper.readValue(s, cla);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return o;
	}
}
