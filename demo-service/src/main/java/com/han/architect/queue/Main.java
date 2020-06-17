package com.han.architect.queue;

import java.util.Random;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Author: hanyf
 * @Description: 使用SynchronousQueue演示生产者、消费者模型
 * @Date: 2020/6/17 18:13
 */
public class Main {

    public static void main(String[] args) {
        SynchronousQueue<Integer> queue = new SynchronousQueue<>();
        new Customer(queue).start();
        new Product(queue).start();
    }

    static class Product extends Thread {
        SynchronousQueue<Integer> queue;

        public Product(SynchronousQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true) {
                int rand = new Random().nextInt(1000);
                System.out.println("生产了一个产品：" + rand);
                System.out.println("等待三秒后运送出去...");
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                queue.offer(rand);
                System.out.println("产品生成完成：" + rand);
            }
        }
    }

    static class Customer extends Thread {
        SynchronousQueue<Integer> queue;

        public Customer(SynchronousQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    System.out.println("消费了一个产品:" + queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("------------------------------------------");
            }
        }
    }
}
/*输出：
生产了一个产品：214
等待三秒后运送出去...
产品生成完成：214
消费了一个产品:214
------------------------------------------
生产了一个产品：713
等待三秒后运送出去...
消费了一个产品:713
------------------------------------------
产品生成完成：713
生产了一个产品：38
等待三秒后运送出去...
产品生成完成：38
生产了一个产品：93
等待三秒后运送出去...
消费了一个产品:38
------------------------------------------*/
