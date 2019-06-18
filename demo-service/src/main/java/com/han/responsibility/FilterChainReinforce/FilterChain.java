package com.han.responsibility.FilterChainReinforce;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: hanyf
 * @Description:
 * @Date: 2018/11/5 14:21
 */
public class FilterChain {
    private List<Filter> filters = new ArrayList<>();
    //调用链上的过滤器时，记录过滤器的位置用
    private int index = 0;

    public FilterChain addFilter(Filter filter) {
        filters.add(filter);
        return this;
    }

    public void doFilter(Request request, Response response) {
        if (index == filters.size()) {
            return;
        }
        //得到当前过滤器
        Filter filter = filters.get(index);
        index++;
        filter.doFilter(request, response, this);
    }
}
