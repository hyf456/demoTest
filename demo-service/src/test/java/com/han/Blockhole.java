package com.han;

/**
 * @Author: hanyf
 * @Description:
 * @Date: 2019/3/11 9:03
 */
public class Blockhole {

    public static void enter(Object obj) {
        System.out.println("Step 1");
        magic(obj);
        System.out.println("Step 2");
        synchronized (obj) {
            System.out.println("Step 3");
        }
    }

    private static void magic(Object obj) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (obj) {
                    System.out.println("1111");
                    while (true) {

                    }
                }
            }
        });
        thread.start();

        try {
            thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        System.out.println("BEGIN");
        Object obj = new Object();
        enter(obj);
        System.out.println("END");
    }
}
