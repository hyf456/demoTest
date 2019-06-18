package com.han.responsibility.Filter;

/**
 * @Author: hanyf
 * @Description: 处理类
 * @Date: 2018/11/5 11:06
 */
public class MsgFilterProcessor {

    private String msg;
    private Filter[] filters = new Filter[]{new HtmlFilter(), new SensitiveFilter()};

    public MsgFilterProcessor(String msg) {
        this.msg = msg;
    }

    public String process() {
        String r = msg;
        for (Filter f : filters) {
            r = f.doFilter(r);
        }
        return r;
    }
}
