// package com.han.commom.util;
//
// import org.springframework.util.CollectionUtils;
//
// import java.util.Collection;
// import java.util.Map;
//
// /**
//  * @Description 用户断言工具类
//  * @Date 2019/7/26 17:47
//  * @Author hanyf
//  */
// public class UserAssertUtil {
//
// 	/**
// 	 * 断言不为空
// 	 * @param obj
// 	 * @param returnCodesEnum
// 	 */
// 	public static void assertNotNull(Object obj, ReturnCodesEnum returnCodesEnum) {
// 		if (obj == null) {
// 			throw new WarnException(returnCodesEnum);
// 		}
// 	}
//
// 	/**
// 	 * 断言为空
// 	 * @param obj
// 	 */
// 	public static void assertNull(Object obj, ReturnCodesEnum returnCodesEnum) {
// 		if (obj != null) {
// 			throw new WarnException(returnCodesEnum);
// 		}
// 	}
//
// 	/**
// 	 * 断言集合不为空
// 	 * @param collection
// 	 * @param returnCodesEnum
// 	 */
// 	public static void assertEmpty(Collection<?> collection, ReturnCodesEnum returnCodesEnum) {
// 		if (!CollectionUtils.isEmpty(collection)) {
// 			throw new WarnException(returnCodesEnum);
// 		}
// 	}
//
// 	/**
// 	 * 断言集合为空
// 	 * @param collection
// 	 */
// 	public static void assertNotEmpty(Collection<?> collection, ReturnCodesEnum returnCodesEnum) {
// 		if (CollectionUtils.isEmpty(collection)) {
// 			throw new WarnException(returnCodesEnum);
// 		}
// 	}
//
// 	/**
// 	 * 断言集合为空(附加属性)
// 	 * @param collection
// 	 */
// 	public static void assertNotEmpty(Collection<?> collection, ReturnCodesEnum returnCodesEnum, Map<String, Object> attachment) {
// 		if (CollectionUtils.isEmpty(collection)) {
// 			throw new WarnException(returnCodesEnum, attachment);
// 		}
// 	}
//
// 	/**
// 	 * 断言字符串不为空
// 	 * @param text
// 	 */
// 	public static void assertNotEmpty(String text, ReturnCodesEnum returnCodesEnum, Map<String, Object> attachment) {
// 		if (StringUtils.isEmpty(text)) {
// 			throw new WarnException(returnCodesEnum, attachment);
// 		}
// 	}
//
// 	/**
// 	 * 断言字符串不为空
// 	 * @param text
// 	 */
// 	public static void assertNotEmpty(String text, ReturnCodesEnum returnCodesEnum) {
// 		if (StringUtils.isEmpty(text)) {
// 			throw new WarnException(returnCodesEnum);
// 		}
// 	}
//
// 	/**
// 	 * 断言表达式true
// 	 * @param expression
// 	 */
// 	public static void assertTrue(Boolean expression, ReturnCodesEnum returnCodesEnum) {
// 		if (expression == null || expression.equals(false)) {
// 			throw new WarnException(returnCodesEnum);
// 		}
// 	}
//
// 	/**
// 	 * 断言表达式true(附加属性)
// 	 * @param expression
// 	 */
// 	public static void assertTrue(Boolean expression, ReturnCodesEnum returnCodesEnum, Map<String, Object> attachment) {
// 		if (expression == null || expression.equals(false)) {
// 			throw new WarnException(returnCodesEnum, attachment);
// 		}
// 	}
//
//
// 	/**
// 	 * 断言表达式false
// 	 * @param expression
// 	 */
// 	public static void assertFalse(Boolean expression, ReturnCodesEnum returnCodesEnum) {
// 		if (expression == null || expression.equals(true)) {
// 			throw new WarnException(returnCodesEnum);
// 		}
// 	}
//
// 	/**
// 	 * 断言表达式false(附加属性)
// 	 * @param expression
// 	 */
// 	public static void assertFalse(Boolean expression, ReturnCodesEnum returnCodesEnum, Map<String, Object> attachment) {
// 		if (expression == null || expression.equals(true)) {
// 			throw new WarnException(returnCodesEnum, attachment);
// 		}
// 	}
//
// 	/**
// 	 * 断言异常
// 	 */
// 	public static void assertError(ReturnCodesEnum returnCodesEnum) {
// 		throw new WarnException(returnCodesEnum);
// 	}
//
// 	/**
// 	 * 断言异常
// 	 */
// 	public static void assertError(ReturnCodesEnum returnCodesEnum, Map<String, Object> attachment) {
// 		throw new WarnException(returnCodesEnum, attachment);
// 	}
// }
