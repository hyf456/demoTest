package com.han;

/**
 * @Author: hanyf
 * @Description:
 * @Date: 2019/4/12 11:36
 */
public class MagimaTest {

    public static void main(String[] args) {
        magimaFunction();
    }
    static MagimaTest st = new MagimaTest();
    static {//静态代码块
        System.out.println("1");
    }
    {//构造代码块
        System.out.println("2");
    }
    private MagimaTest() {//构造方法
        System.out.println("3");
        System.out.println("a=" + a + ",b=" + b);
    }
    public static void magimaFunction() {
        System.out.println("4");
    }
    int a = 110;
    static int b = 112;
}
