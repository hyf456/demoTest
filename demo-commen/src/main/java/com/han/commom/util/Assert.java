package com.han.commom.util;

import com.yestae.base.user.model.exception.ErrorCode;
import com.yestae.base.user.model.exception.StatusDefinition;
import com.yestae.framework.common.exception.SystemException;

/**
 * @Description 验证数据类型
 * @Date 2019/8/12 16:06
 * @Author hanyf
 */
public class Assert {

	/**
	 * 验证value 必须在min(含) ~ max(含)之间
	 */
	public static void between(long value, int min, int max, String msg) {
		if (value < min || value > max) {
			throw new SystemException(ErrorCode.PARAM_ERROR.getErrorCode(), msg);
		}
	}

	/**
	 * 验证value不为空或null
	 */
	public static void isNotBlank(String value, String msg) {
		isNotBlank(value, ErrorCode.PARAM_ERROR, msg);
	}

	public static void isNotBlank(String value, StatusDefinition statusDefinition, String msg) {
		if (StringUtils.isBlank(value)) {
			throw new SystemException(statusDefinition.getErrorCode(), msg);
		}
	}

	/**
	 * 验证value是null
	 */
	public static void isNull(Object value, String msg) {
		if (value != null) {
			throw new SystemException(ErrorCode.PARAM_ERROR.getErrorCode(), msg);
		}
	}

	/**
	 * 验证value不是null
	 */
	public static void isNotNull(Object value, String msg) {
		isNotNull(value, ErrorCode.PARAM_ERROR, msg);
	}

	public static void isNotNull(Object value, StatusDefinition statusDefinition, String msg) {
		if (value == null) {
			throw new SystemException(statusDefinition.getErrorCode(), msg);
		}
	}

	/**
	 * 验证result不为空
	 */
	public static void isNotEmpty(Object[] result, String msg) {
		if (result == null || result.length == 0) {
			throw new SystemException(ErrorCode.PARAM_ERROR.getErrorCode(), msg);
		}
	}

	/**
	 * 验证value限定在includes范围内
	 */
	public static void in(Object value, String msg, Object... includes) {
		for (Object obj : includes) {
			if (obj.equals(value)) {
				return;
			}
		}
		throw new SystemException(ErrorCode.PARAM_ERROR.getErrorCode(), msg);
	}

	/**
	 * 验证value==true
	 */
	public static void isTrue(boolean value, String msg) {
		isTrue(value, ErrorCode.PARAM_ERROR, msg);
	}

	public static void isTrue(boolean value, StatusDefinition statusDefinition, String msg) {
		if (!value) {
			throw new SystemException(statusDefinition.getErrorCode(), msg);
		}
	}

	public static void isFalse(boolean value, String msg) {
		if (value) {
			throw ErrorCode.PARAM_ERROR.throwError(msg);
		}
	}

	/**
	 * 验证value是int
	 */
	public static void isInt(String value, String msg) {
		isInt(value, ErrorCode.PARAM_ERROR, msg);
	}

	public static void isInt(String value, StatusDefinition statusDefinition, String msg) {
		try {
			Integer.parseInt(value);
		} catch (NumberFormatException e) {
			throw new SystemException(statusDefinition.getErrorCode(), msg);
		}
	}

	/**
	 * 验证value是long
	 */
	public static void isLong(String value, String msg) {
		isLong(value, ErrorCode.PARAM_ERROR, msg);
	}

	public static void isLong(String value, StatusDefinition statusDefinition, String msg) {
		try {
			Long.parseLong(value);
		} catch (NumberFormatException e) {
			throw new SystemException(statusDefinition.getErrorCode(), msg);
		}
	}
}
