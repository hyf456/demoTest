package com.han.commom.jd.utils;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: jl-studio
 * @description:
 * @author: yangh
 * @create: 2020-09-17 18:37
 **/
@Data
public class PageData<T> {

    private long totalElements = 0;

    private int size = 10;

    private int page = 1;

    private List<T> content = new ArrayList<T>();

    public PageData() {
    }

    public PageData(int size, int page) {
        this.size = size;
        this.page = page;
    }

    public PageData(long totalElements, int size, int page, List<T> content) {
        this.totalElements = totalElements;
        this.size = size;
        this.page = page;
        this.content = content;
    }


}
