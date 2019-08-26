package com.han.commom.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.BeanUtils;

import java.util.Collection;
import java.util.List;

/**
 * @Description 类转化器
 * 借助FastJson从一个类转化为另一个类
 * 他们具有基本相同的属性名称
 * @Date 2019/7/30 11:35
 * @Author hanyf
 */
public class ClazzConverterUtil extends BeanUtils {

	/**
	 * @Author hanyf
	 * @Description 具有相同属性名称的对象转化
	 * @Date 11:36 2019/7/30
	 * @Param srcClazz 源类
	 * @param dstClazz 目标类
	 * @return T1
	 **/
	public static <T1, T2> T1 converterClass(final T2 srcClazz, Class<T1> dstClazz){
		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(srcClazz);
		if(jsonObject == null){
			return null;
		}
		return JSONObject.toJavaObject(jsonObject, dstClazz);
	}

	/**
	 * @Author hanyf
	 * @Description 集合转化
	 * @Date 11:36 2019/7/30
	 * @Param srcClazzCollection 源集合
	 * @param dstClazz 目标类
	 * @return java.util.Collection<T1>
	 **/
	public static <T1, T2> Collection<T1> converterClass(final Collection<T2> srcClazzCollection, Class<T1> dstClazz){
		JSONArray jsonArray = (JSONArray) JSONObject.toJSON(srcClazzCollection);
		if(jsonArray == null){
			return null;
		}
		return JSONArray.parseArray(jsonArray.toJSONString(), dstClazz);
	}

	/**
	 * @Author hanyf
	 * @Description 集合转化
	 * @Date 11:36 2019/7/30
	 * @Param srcClazzCollection 源集合
	 * @param dstClazz 目标类
	 * @return java.util.Collection<T1>
	 **/
	public static <T1, T2> List<T1> converterClassList(final List<T2> srcClazzCollection, Class<T1> dstClazz){
		JSONArray jsonArray = (JSONArray) JSONObject.toJSON(srcClazzCollection);
		if(jsonArray == null){
			return null;
		}
		return JSONArray.parseArray(jsonArray.toJSONString(), dstClazz);
	}
	/**
	 * @Author hanyf
	 * @Description 数组转化
	 * @Date 11:37 2019/7/30
	 * @Param srcClazzArray 源数组
	 * @return dstClazz  目标类
	 **/
	@SuppressWarnings("unchecked")
	public static <T1, T2> T1[] converterClass(final T2[] srcClazzArray, Class<T1> dstClazz){
		JSONArray jsonArray = (JSONArray) JSONObject.toJSON(srcClazzArray);
		if(jsonArray == null){
			return null;
		}
		List<T1> result = JSONArray.parseArray(jsonArray.toJSONString(), dstClazz);
		if(result == null){
			return null;
		}
		return (T1[])result.toArray();
	}

	/**
	 * 将json转对象
	 * @param json
	 * @param dstClazz
	 * @param <T>
	 * @return
	 */
	public static <T> T jsonToObject(final String json, Class<T> dstClazz){
		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(json);
		return JSONObject.toJavaObject(jsonObject, dstClazz);
	}

	/**
	 * 将对象转Json
	 * @param srcClazz
	 * @param <T>
	 * @return
	 */
	public static <T> String objectToJson(final T srcClazz){
		return JSONObject.toJSONString(srcClazz);
	}

	//public static void main(String[] args) {
	//	BaseInfoEntity baseInfoEntity=new BaseInfoEntity();
	//	baseInfoEntity.setCode("test").setCompanyName("dayi");
	//	String json = objectToJson(baseInfoEntity);
	//	System.out.println(json);
	//	System.out.println(baseInfoEntity);
	//}
}
