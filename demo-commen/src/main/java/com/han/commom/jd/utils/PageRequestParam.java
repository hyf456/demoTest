package com.han.commom.jd.utils;

import lombok.Data;

import java.io.Serializable;

@Data
public class PageRequestParam implements Serializable {

    private int page = 1;

    private int pageSize = 10;

}