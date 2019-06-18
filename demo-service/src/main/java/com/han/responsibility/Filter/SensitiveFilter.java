package com.han.responsibility.Filter;

/**
 * @Author: hanyf
 * @Description:
 * @Date: 2018/11/5 11:04
 */
public class SensitiveFilter implements Filter {

    @Override
    public String doFilter(String string) {
        String r = string;
        //过滤string中的敏感信息
        r = r.replaceAll("敏感信息", "").replace("被就业", "就业");
        return r;
    }
}
