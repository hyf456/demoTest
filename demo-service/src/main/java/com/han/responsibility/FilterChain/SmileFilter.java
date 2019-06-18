package com.han.responsibility.FilterChain;

import com.han.responsibility.Filter.Filter;

/**
 * @Author: hanyf
 * @Description:
 * @Date: 2018/11/5 13:56
 */
public class SmileFilter implements Filter {
    @Override
    public String doFilter(String string) {
        String r = string;
        //过滤msg中的笑脸标记
        r = r.replace(":)", "^_^");
        return r;
    }
}
