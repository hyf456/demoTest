package com.han.practise;

/**
 * @Author:
 * @Description: 资源共享
 * @Date: 2018/4/9 12:33
 */
public class Thread2 implements Runnable {
    private int count=15;
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + "运行  count= " + count--);
            try {
                Thread.sleep((int) Math.random() * 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
