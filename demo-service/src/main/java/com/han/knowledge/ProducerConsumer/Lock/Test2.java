package com.han.knowledge.ProducerConsumer.Lock;

/**
 * Created by hp on 2017-04-26.
 */
public class Test2 {

    public static void main(String[] args) {

        // 仓库对象
        Storehouse storage = new Storehouse(1000);

        // 生产者对象
        ProducerThread p1 = new ProducerThread(storage, 200);
        ProducerThread p2 = new ProducerThread(storage, 200);
        ProducerThread p3 = new ProducerThread(storage, 100);
        ProducerThread p4 = new ProducerThread(storage, 300);
        ProducerThread p5 = new ProducerThread(storage, 400);
        ProducerThread p6 = new ProducerThread(storage, 200);
        ProducerThread p7 = new ProducerThread(storage, 500);

        // 消费者对象
        ConsumerThread c1 = new ConsumerThread(storage, 500);
        ConsumerThread c2 = new ConsumerThread(storage, 200);
        ConsumerThread c3 = new ConsumerThread(storage, 800);

        // 线程开始执行
        c1.start();
        c2.start();
        c3.start();
        p1.start();
        p2.start();
        p3.start();
        p4.start();
        p5.start();
        p6.start();
        p7.start();

    }
}
