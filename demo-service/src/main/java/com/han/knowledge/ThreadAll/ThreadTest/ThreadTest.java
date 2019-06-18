package com.han.knowledge.ThreadAll.ThreadTest;

/**
 * Created by hp on 2017-04-21.
 * 实现功能：子线程循环10次，接着主线程循环15次，接着又回到子线程循环10次，接着再回到主线程又循环15次，如此循环50次。
 */
public class ThreadTest {

    private static Object object = new Object();
    public static void main(String[] args) throws InterruptedException {
        // TODO Auto-generated method stub
        new Thread(new Runnable() {

            public void run() {
                // TODO Auto-generated method stub
                for (int i = 0; i < 50; i++) {
                    synchronized (object) {
                        for (int j = 0; j < 10; j++) {
                            System.out.println("子循环在循环：ThreadCount == " + (j+1));
                        }
                        System.out.println("子循环执行了 --->"+(i+1)+"个10次");
                        object.notify();
                        try {
                            object.wait();
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();

        for(int i = 0; i < 50; i++){
            System.out.println("zhu");
            synchronized (object) {
                //主线程让出锁，等待子线程唤醒
                object.wait();
                for (int j = 0; j < 15; j++) {
                    System.out.println("主循环在循环：MainCount == " + (j+1));
                }
                System.out.println("主循环执行了 --->"+(i+1)+"个15次");
                object.notify();
            }
        }
    }
}
