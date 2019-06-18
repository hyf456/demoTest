package com.han.responsibility.FilterChainReinforce;

/**
 * @Author: hanyf
 * @Description:
 * @Date: 2018/11/5 14:30
 */
public class MainClass {
    public static void main(String[] args) {
        // 需要被过滤的语句
        String msg = "被就业了：），敏感信息，<script>";
        //创建Request、Response对象
        Request req = new Request();
        Response resp = new Response();
        req.reqStr = msg;
        resp.respStr = "response";
        //搞一个过滤链，链上有两个过滤器
        FilterChain chain = new FilterChain();
        chain.addFilter(new HtmlFilter())
                .addFilter(new SensitiveFilter())
        .addFilter(new HtmlFilter());
        //开始过滤
        chain.doFilter(req, resp);
        System.out.println(req.reqStr);
        System.out.println(resp.respStr);
    }
}
