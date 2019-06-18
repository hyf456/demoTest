package com.han.servlet;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: hanyf
 * @Description: 两个线程分别打印奇偶数（优化后的策略）
 * @Date: 2019/5/29 10:30
 */
public class AlibabaTest {

    private static volatile Boolean flag = true;

    private static AtomicInteger num = new AtomicInteger();

    private static final Integer TOTAL = 100;

    public static CountDownLatch latch = new CountDownLatch(2);

    public static void main(String[] args) throws InterruptedException {

        long start =  System.currentTimeMillis();


        Thread jsThread = new Thread(new Runnable() {

            @Override
            public void run() {

                while(num.get() <= TOTAL-1){
                    if(!flag){
                        System.out.println(Thread.currentThread().getName()+"打印: " + num.getAndIncrement());
                        flag = true;
                    }
                }

                latch.countDown();

            }
        });

        jsThread.setName("奇数线程");

        Thread osThread = new Thread(new Runnable() {

            @Override
            public void run() {

                while(num.get() <= TOTAL){
                    if(flag){
                        System.out.println(Thread.currentThread().getName()+ "打印： " + num.getAndIncrement());
                        flag = false;
                    }
                }

                latch.countDown();
            }
        });

        osThread.setName("偶数线程");

        osThread.start();
        jsThread.start();

        latch.await();
        long end =  System.currentTimeMillis();

        System.out.println("一共耗时："+(end - start) + "ms");

    }

}
