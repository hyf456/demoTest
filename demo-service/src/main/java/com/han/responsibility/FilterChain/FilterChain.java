package com.han.responsibility.FilterChain;

import com.han.responsibility.Filter.Filter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: hanyf
 * @Description:
 * @Date: 2018/11/5 13:52
 */
public class FilterChain implements Filter {

    public List<Filter> filters = new ArrayList<Filter>();

    public FilterChain addFilter(Filter f) {
        filters.add(f);
        return this;
    }

    @Override
    public String doFilter(String string) {
        String r = string;
        for (Filter f : filters) {
            r = f.doFilter(r);
        }
        return r;
    }
}
