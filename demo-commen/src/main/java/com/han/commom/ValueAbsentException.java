package com.han.commom;

/**
 * @Author: hanyf
 * @Description:
 * @Date: Created by in 15:26 2018/3/6
 */
public class ValueAbsentException extends Throwable {

    public ValueAbsentException() {
        super();
    }

    public ValueAbsentException(String msg) {
        super(msg);
    }

    @Override
    public String getMessage() {
        return "No value present in the Optional instance";
    }
}
