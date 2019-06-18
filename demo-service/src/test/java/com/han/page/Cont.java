package com.han.page;

import lombok.ToString;
import org.elasticsearch.common.recycler.Recycler;

import java.util.Optional;

/**
 * @Author: hanyf
 * @Description:
 * @Date: 2019/3/12 9:57
 */
@ToString
public class Cont extends Page {

    private int aaa;

    private String sss;

    public Cont() {
    }

    public int getAaa() {
        return aaa;
    }

    public void setAaa(int aaa) {
        this.aaa = aaa;
    }

    public String getSss() {
        return sss;
    }

    public void setSss(String sss) {
        this.sss = sss;
    }

    public static void main(String[] args) {
        Cont cont = new Cont();
        cont.setPage(3);
        cont.setSize(2);
        cont.setPagea(0);
        System.out.println(cont.toString());
        Optionals<String> of = Optionals.of(cont.getSss(), V::NE);
    }

}

class V {
    private String NE;

    public static String NE() {
        return "0";
    }
}
