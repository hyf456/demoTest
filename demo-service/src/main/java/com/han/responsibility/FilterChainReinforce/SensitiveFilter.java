package com.han.responsibility.FilterChainReinforce;

/**
 * @Author: hanyf
 * @Description:
 * @Date: 2018/11/5 14:29
 */
public class SensitiveFilter implements Filter {

    @Override
    public void doFilter(Request req, Response response, FilterChain chain) {
        //过滤req.reqStr中的敏感词
        req.reqStr = req.reqStr.replace("敏感", "").replace("被就业", "就业");
        req.reqStr += "===SensitiveFilter";
        chain.doFilter(req, response);
        response.respStr += "===SensitiveFilter";
    }
}
