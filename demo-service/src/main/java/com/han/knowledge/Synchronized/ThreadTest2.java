package com.han.knowledge.Synchronized;

/**
 * Created by hp on 2017-05-24.
 */
public class ThreadTest2 extends Thread {

    private int threadNo;
    private String lock;
    public ThreadTest2(int threadNo, String lock) {
        this.threadNo = threadNo;
        this.lock = lock;
    }

    public static void main(String[] args) throws Exception {
        String lock = new String("lock");
        for (int i = 1; i < 10; i++) {
            new ThreadTest2(i, lock).start();
            Thread.sleep(1);
        }
    }
    public void run() {
        synchronized (lock) {
            for (int i = 1; i < 10000; i++) {
                System.out.println("No." + threadNo + ":" + i);
            }
        }
    }
}
