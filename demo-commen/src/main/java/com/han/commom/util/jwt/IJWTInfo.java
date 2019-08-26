package com.han.commom.util.jwt;

/**
 * @Description TODO
 * @Date 2019/7/26 15:46
 * @Author hanyf
 */
public interface IJWTInfo {

	/**
	 * 获取用户名
	 * @return
	 */
	String getUniqueName();

	/**
	 * 获取用户ID
	 * @return
	 */
	String getId();

	/**
	 * 获取名称
	 * @return
	 */
	String getName();
}
