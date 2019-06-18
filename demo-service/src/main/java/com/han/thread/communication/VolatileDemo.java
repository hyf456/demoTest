package com.han.thread.communication;

import java.util.concurrent.CountDownLatch;

/**
 * @Author: hanyf
 * @Description: volatile 共享内存
 * @Date: 2018/11/8 18:49
 */
public class VolatileDemo implements Runnable {

    private static volatile boolean flag = true;

    @Override
    public void run() {
        while (flag) {
            System.out.println(Thread.currentThread().getName() + "正在运行...");
        }
        System.out.println(Thread.currentThread().getName() + "执行完毕");
    }

    private void stopThread() {
        flag = false;
    }

    public static void main(String[] args) throws Exception {
        //VolatileDemo volatileDemo = new VolatileDemo();
        //new Thread(volatileDemo, "thread A").start();
        //
        //System.out.println("main 线程正在运行");
        //TimeUnit.MILLISECONDS.sleep(100);
        //volatileDemo.stopThread();
        countDownLatch();
    }


    /**   
     *
     *功能描述 CountDownLatch 并发工具 实现join功能
     * @author hanyf
     * @date 2018/11/8
     * @param [] 
     * @return void
     */
    private static void countDownLatch() throws Exception {
        int thread = 3;
        long start = System.currentTimeMillis();
        final CountDownLatch countDown = new CountDownLatch(thread);
        for (int i = 0; i < thread; i++) {
            new Thread(() -> {
                System.out.println("thread run");
                try {
                    Thread.sleep(2000);
                    countDown.countDown();
                    System.out.println("thread end");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        countDown.await();
        long stop = System.currentTimeMillis();
        System.out.println("main over total time = " + (stop - start));
    }
}
