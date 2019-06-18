package com.han.thread.communication;

/**
 * @Author: hanyf
 * @Description: 两个线程交替打印奇偶数
 * @Date: 2018/11/8 17:59
 */
public class TwoThreadWaitNotify {

    private int start = 1;

    private boolean flag = false;

    public static void main(String[] args) {
        TwoThreadWaitNotify twoThreadWaitNotify = new TwoThreadWaitNotify();

        Thread t1 = new Thread(new OuNum(twoThreadWaitNotify));
        t1.setName("A");

        Thread t2 = new Thread(new JiNum(twoThreadWaitNotify));
        t2.setName("B");

        t1.start();
        t2.start();

    }

    /**   
     *
     *功能描述 偶数线程
     * @author hanyf
     * @date 2018/11/8
     * @param  
     * @return 
     */
    public static class OuNum implements Runnable {
        private TwoThreadWaitNotify number;

        public OuNum(TwoThreadWaitNotify number) {
            this.number = number;
        }

        @Override
        public void run() {
            while (number.start <= 100) {
                synchronized (TwoThreadWaitNotify.class) {
                    System.out.println("偶数线程抢到锁了");
                    if (number.flag) {
                        System.out.println(Thread.currentThread().getName() + "+ - +偶数" + number.start);
                        number.start++;

                        number.flag = false;

                        TwoThreadWaitNotify.class.notify();
                    } else {
                        try {
                            TwoThreadWaitNotify.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /**   
     *
     *功能描述  奇数线程
     * @author hanyf
     * @date 2018/11/8
     * @param  
     * @return 
     */
    public static class JiNum implements Runnable {

        private TwoThreadWaitNotify number;

        public JiNum(TwoThreadWaitNotify number) {
            this.number = number;
        }

        @Override
        public void run() {
            while (number.start <= 100) {
                synchronized (TwoThreadWaitNotify.class) {
                    System.out.println("奇数线程抢到了锁");
                    if (!number.flag) {
                        System.out.println(Thread.currentThread().getName() + "+ - +奇数" + number.start);
                        number.start++;

                        number.flag = true;

                        TwoThreadWaitNotify.class.notify();
                    } else {
                        try {
                            TwoThreadWaitNotify.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

}
