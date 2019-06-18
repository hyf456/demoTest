package com.han.page;

import lombok.ToString;

/**
 * @Author: hanyf
 * @Description:
 * @Date: 2019/3/12 9:55
 */
@ToString
public class Page {

    private int page;

    private int size;

    private int pagea = 0;

    private int sizea = 0;

    public Page() {
        this.page = this.page;
        this.pagea = (page - 1) * size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPagea() {
        return pagea;
    }

    public void setPagea(int pagea) {
        this.pagea = (page - 1) * size;
    }

    public int getSizea() {
        return size;
    }

    public void setSizea(int sizea) {
        this.sizea = size;
    }
}
