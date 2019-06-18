package com.han.knowledge.Generic;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by hanyf on 2017-04-10.
 */
public class TestGeneric {

    /**
     * 泛型与继承的关系:
     * 若类A是类B的子类，那么List<A>就不是List<B>的子接口
     */
    @Test
    public void test1() {
        Object obj = null;
        String str = new String("AA");
        obj = str;//向上转型

        Object[] obj1 = null;
        String[] str1 = new String[]{"AAA","BBB"};
        obj1 = str1;//向上转型

        List<Object> lo = null;
        List<String> ls = new ArrayList<String>();
        //lo = ls;//错误，List<String>不是List<Object>的子接口
    }

    /*
     * 通配符 ?
     * List<A>、List<B>、。。。。都是List<?>的子类
     *
     * ? extends A :可以存放A及其子类
     * ? super A:可以存放A及其父类
     */
    @Test
    public void test2() {
        List<?> list = null;
        List<Object> lo = new ArrayList<Object>();
        List<String> ls = new ArrayList<String>();
        list = lo;
        list = ls;
        show1(lo);

        List<? extends Number> ln = null;
        List<Integer> lin = new ArrayList<Integer>();
        ln = lin;
        List<Object> lob = new ArrayList<Object>();
        //ln = lob;//错误，? extends A :可以存放A及其子类

        List<? super Number> ln2 = null;
        ln2 = lob;
        //ln2 = lin;//错误，? super A:可以存放A及其父类
    }

    public <T> void show1(List<T/*此处T相当于传进来的类型*/> list) {

    }
    public void show2(List<?/*此处?相当于传进来的类型的父接口类型*/> list) {

    }

    @Test
    public void test3() {
        List<String> ls = new ArrayList<String>();
        ls.add("aa");
        ls.add("bb");
        ls.add("cc");
        List<?> li = ls;
        //可以读取声明为通配符的集合对象
        Iterator it = li.iterator();
        while(it.hasNext()) {
            Object o = it.next();
            System.out.println(o);
        }

        //不能写进声明为通配符的集合对象，除了唯一例外的null
        //li.add("dd");//错误
        //li.add(12);//错误
        li.add(null);

        List<String> lstr = (List<String>)li;//强转回原来的类型就可以写进
        lstr.add("ll");
        System.out.println(lstr);
    }
}
