package com.han.knowledge.ThreadAll.ThreadDaemon;

/**
 * Created by hanyf on 2017-03-23.
 */
public class ThreadDaemon {

    public static void main(String[] args) {
        ThreadDaemon thread=new ThreadDaemon();
        Thread t1 = thread.new MyThread1();
        Thread t2 = new Thread(thread.new MyRunnable());
        t2.setDaemon(true); //设置为守护线程

        t2.start();
        t1.start();
    }

    class MyThread1 extends Thread {
        public void run() {
            for (int i = 0; i < 5; i++) {
                System.out.println("线程1第" + i + "次执行！");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class MyRunnable implements Runnable {
        public void run() {
            for (long i = 0; i < 9999999L; i++) {
                System.out.println("后台线程第" + i + "次执行！");
                try {
                    Thread.sleep(7);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
