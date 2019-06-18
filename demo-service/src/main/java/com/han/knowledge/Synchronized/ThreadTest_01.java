package com.han.knowledge.Synchronized;

/**
 * Created by hp on 2017-05-24.
 */
public class ThreadTest_01 implements Runnable {

    @Override
    public synchronized void run() {
        for(int i = 0 ; i < 3 ; i++){
            System.out.println(Thread.currentThread().getName() + "run......");
        }
    }

    public static void main(String[] args) {
        ThreadTest_01 threadTest = new ThreadTest_01();
        for(int i = 0 ; i < 5 ; i++){
//            newcache Thread(newcache ThreadTest_01(),"Thread_" + i).start();
            new Thread(threadTest,"Thread_" + i).start();
        }
    }
}
