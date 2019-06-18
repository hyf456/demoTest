package com.han.responsibility.FilterChain;

/**
 * @Author: hanyf
 * @Description:
 * @Date: 2018/11/5 13:57
 */
public class MsgFilterChainProcessor {

    private String msg;
    private FilterChain chain = new FilterChain();


    public MsgFilterChainProcessor(String msg, FilterChain filterChain) {
        this.msg = msg;
        this.chain = filterChain;
    }

    public String process() {
        return chain.doFilter(msg);
    }
}
