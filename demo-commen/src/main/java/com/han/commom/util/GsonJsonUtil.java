package com.han.commom.util;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description Gson 对象转换工具类
 * @Date 2019/8/12 16:00
 * @Author hanyf
 */
public class GsonJsonUtil {

	private static Gson gson = null;

	static {
		if (gson == null) {
			gson = GsonUtils.getGson();
		}
	}

	/**
	 * 将object对象转成json字符串
	 *
	 * @param object
	 * @return
	 */
	public static String GsonString(Object object) {
		String gsonString = null;
		if (gson != null) {
			gsonString = gson.toJson(object);
		}
		return gsonString;
	}

	/**
	 * 将gsonString转成泛型bean
	 *
	 * @param gsonString
	 * @param cls
	 * @return
	 */
	public static <T> T GsonToBean(String gsonString, Class<T> cls) {
		T t = null;
		if (gson != null) {
			t = gson.fromJson(gsonString, cls);
		}
		return t;
	}

	/**
	 * 将gsonString转成泛型两层嵌套的bean
	 * @Title: GsonToBeanIteration
	 * @Description:
	 * @param @param gsonString  gson格式数据
	 * @param @param cls	第一层数据class
	 * @param @param clz 嵌套数据class
	 * @param @param methodName 获得嵌套数据的方法名
	 * @param @param parameterTypes 参数类型
	 * @param @param args 参数
	 * @param @return    参数
	 * @return S    返回类型
	 * @throws
	 *//*
	@SuppressWarnings("unchecked")
	public static <T, S> T GsonToBeanIteration(String gsonString, Class<T> cls,Class<S> clz,String getMethodName,Class<?> parameterTypes,Object args,String setMethodName) {
		T t = null;
		S s=null;
		if (gson != null) {
			t = gson.fromJson(gsonString, cls);
		}
		try {
			Method  getMethod = cls.getMethod(getMethodName, parameterTypes);
			Method  setMethod = cls.getMethod(setMethodName, clz);
			Object object=getMethod.invoke(t, args);
			String gsonStr= GsonString(object);
			s=GsonToBean(gsonStr, clz);
			t=(T) setMethod.invoke(t, s);
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return t;
	}*/

	/**
	 * 转成list 泛型在编译期类型被擦除导致报错
	 *
	 * @param gsonString
	 * @param cls
	 * @return
	 */
	public static <T> List<T> GsonToList(String gsonString, Class<T> cls) {
		List<T> list = null;
		if (gson != null) {
			list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
			}.getType());
		}
		return list;
	}

	/**
	 * 转成list 解决泛型问题
	 *
	 * @param json
	 * @param cls
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> jsonToList(String json, Class<T> cls) {
		Gson gson = new Gson();
		List<T> list = new ArrayList<T>();
		JsonArray array = new JsonParser().parse(json).getAsJsonArray();
		for (final JsonElement elem : array) {
			list.add(gson.fromJson(elem, cls));
		}
		return list;
	}

	/**
	 * 转成list中有map的
	 *
	 * @param gsonString
	 * @return
	 */
	public static <T> List<Map<String, T>> GsonToListMaps(String gsonString) {
		List<Map<String, T>> list = null;
		if (gson != null) {
			list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
			}.getType());
		}
		return list;
	}

	/**
	 * 转成map的
	 *
	 * @param gsonString
	 * @return
	 */
	public static <T> Map<String, T> GsonToMaps(String gsonString) {
		Map<String, T> map = null;
		if (gson != null) {
			map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
			}.getType());
		}
		return map;
	}
}
