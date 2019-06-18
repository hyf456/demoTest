package com.han.practise;

/**
 * @Author:
 * @Description: 多线程
 * @Date: 2018/4/9 12:36
 */
public class Thread1 extends Thread {
    private String name;
    public Thread1(String name) {
        this.name=name;
    }
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(name + "运行  :  " + i);
            try {
                sleep((int) Math.random() * 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
