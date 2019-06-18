package com.han;

/**
 * @Author: hanyf
 * @Description:
 * @Date: 2019/2/26 11:21
 */
public class Null {

    public static void greet() {
        System.out.println("Hello world!");
    }

    public static void main(String[] args) {
        Null x =null;
        x.greet();
        ((Null)x).greet();
        ((Null)null).greet();
        String aa = "a";
        aa.hashCode();
    }
}
