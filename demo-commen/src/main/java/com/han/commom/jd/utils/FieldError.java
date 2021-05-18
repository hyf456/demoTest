package com.han.commom.jd.utils;

/**
 * @ClassName FieldError
 * @Description TODO
 * @Author hanyunfei1
 * @Date 2021/5/7 20:43
 * @Version 1.0
 **/
public class FieldError {

	private String code;

	private String field;

	private String message;

	public FieldError() {
	}

	public FieldError(String code, String field, String message) {
		this.code = code;
		this.field = field;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "{code=" + code +
				", field=" + field +
				", message=" + message + '}';
	}
}
