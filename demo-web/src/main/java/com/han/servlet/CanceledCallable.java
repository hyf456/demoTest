package com.han.servlet;

import javax.servlet.AsyncContext;

/**
 * @Author: hanyf
 * @Description:
 * @Date: 2018/7/20 14:23
 */
public class CanceledCallable implements ICanceledCallabled {

    private  AsyncContext asyncContext;

    public CanceledCallable( AsyncContext asyncContext) {
        this.asyncContext = asyncContext;
    }

    @Override
    public Object call() throws Exception {
        return null;
    }
}
