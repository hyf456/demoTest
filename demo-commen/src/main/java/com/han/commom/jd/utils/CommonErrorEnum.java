package com.han.commom.jd.utils;

/**
 * @ClassName CommonErrorEnum
 * @Description TODO
 * @Author hanyunfei1
 * @Date 2021/5/7 20:44
 * @Version 1.0
 **/
public enum CommonErrorEnum {

	SYSTEM_ERROR("systemError","系统未知异常"),
	BINDING_ERROR("bindingError","参数绑定异常"),
	;

	CommonErrorEnum(String code,String message) {
		this.code = code;
		this.message = message;
	}

	private String code;

	private String message;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
