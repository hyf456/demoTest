package com.han.responsibility.FilterChainReinforce;



/**
 * @Author: hanyf
 * @Description:
 * @Date: 2018/11/5 14:15
 */
public interface Filter {
    void doFilter(Request req, Response response, FilterChain chain);
}
