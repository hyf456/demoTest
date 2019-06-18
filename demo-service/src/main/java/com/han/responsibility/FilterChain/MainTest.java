package com.han.responsibility.FilterChain;

import com.han.responsibility.Filter.HtmlFilter;
import com.han.responsibility.Filter.SensitiveFilter;

/**
 * @Author: hanyf
 * @Description: 、
 * @Date: 2018/11/5 13:59
 */
public class MainTest {

    public static void main(String[] args) {

        //需要被过滤的语句
        String msg = "被就业了:),敏感信息,<script>";
        //实例化处理类
        //MsgProcessor mp = new MsgProcessor(msg);
        //MsgFilterProcessor mp = new MsgFilterProcessor(msg);

        //搞一个过滤链
        FilterChain filterChain = new FilterChain();
        filterChain.addFilter(new HtmlFilter())
                .addFilter(new SensitiveFilter())
                .addFilter(new SmileFilter());
        //实例化处理类
        MsgFilterChainProcessor msgFilterChainProcessor = new MsgFilterChainProcessor(msg, filterChain);
        String process = msgFilterChainProcessor.process();
        System.out.println(process);
    }
}
