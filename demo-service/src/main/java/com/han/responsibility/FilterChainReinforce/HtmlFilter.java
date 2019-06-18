package com.han.responsibility.FilterChainReinforce;

/**
 * @Author: hanyf
 * @Description:
 * @Date: 2018/11/5 14:27
 */
public class HtmlFilter implements Filter {
    @Override
    public void doFilter(Request req, Response response, FilterChain chain) {
        //过滤req.reqStr中的HTML编辑
        req.reqStr = req.reqStr.replace("<", "<").replace(">", ">");
        req.reqStr += "---HtmlFilter()---";
        chain.doFilter(req, response);
        response.respStr += "---HtmlFilter()---";
    }
}
