package com.han.knowledge.Genericity;

/**
 * Created by hp on 2017-04-10.
 */
public class GenericityTest {
    public static void main(String[] args) {
        // 这里只测试Son6和Son8 其他很简单，自己测试吧！
        System.out.println(new Son6<String>("son6", "son6"));// 构造函数两个参数类型一样的
        System.out.println(new Son8<Number, Long>(8, 8L));// 构造函数 两个参数具有父子关系
    }
}