package com.han.knowledge.InternalStorage;

/**
 * Created by hp on 2017-06-09.
 */
public class LongTest {

    public static int a = 0;

    public static void r() {
        new Thread() {
            @Override
            public void run() {
                while (true) {
//long a=com.han.knowledge.Test.a;
                    if (a != 0 || a != -1) {
                        System.out.println(a);
                        System.out.println("r");
                    }
                }

            }
        }.start();
    }

    public static void w() {
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    a = a == -1 ? 0 : -1;
                    System.out.println("w");
                }

            }
        }.start();
    }

    public static void main(String[] args) throws Exception {
        r();
        w();
    }

}
