package com.han.commom.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;

/**
 * @Description TODO
 * @Date 2019/8/12 16:02
 * @Author hanyf
 */
public class GsonUtils {

	public static Gson getGson() {
		GsonBuilder gb = new GsonBuilder();
		gb.setLongSerializationPolicy(LongSerializationPolicy.STRING);
		return gb.create();
	}
}
