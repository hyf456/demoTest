package com.han.knowledge;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: hanyf
 * @Description:
 * @Date: Created by in 15:49 2017/8/30
 */
public class Test1 {
    static class  A {
        static final int CONSTANT = 1;
        static {
            System.out.println("a");
        }
    }
    static class B extends  A {
        static {
            System.out.println("b");
        }
    }

    public static void main(String[] args) {
//        B t = newcache B();
//        System.out.println(B.CONSTANT);
//        String a = "a";
//        String b = "b";
//        swapString(a, b);
//        System.out.println("a:"+ a + "b: "+b);
        List<String> list = new ArrayList<String>();
        list.add("a");
        list.add(null);
        list.add("b");
        list.add(null);
        list.add("c");
        getList(list);
    }

    public static void swapString (String a, String b ) {
        String temp = a;
        a = b;
        b = temp;
    }

    public static void getList(List<String>list) {
        for (Object i : list) {
            System.out.println(i);
        }
    }
    public static void getListB(List<Object>list) {
        for (Object i : list) {
            System.out.println(i);
        }
    }
}
