package com.han.responsibility.Filter;

/**
 * @Author: hanyf
 * @Description:
 * @Date: 2018/11/5 11:02
 */
public class HtmlFilter implements Filter {

    @Override
    public String doFilter(String string) {
        String r = string;
        //过滤msg中的HTML标记
        r = r.replaceAll("<.*>", "");
        return r;
    }
}

