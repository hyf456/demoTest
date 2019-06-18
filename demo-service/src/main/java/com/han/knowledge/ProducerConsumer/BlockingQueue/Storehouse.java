package com.han.knowledge.ProducerConsumer.BlockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by hp on 2017-04-26.
 */
public class Storehouse {

    // 仓库的容量
    private int capacity;
    // 仓库存储的载体
    private BlockingQueue<Object> blockingQueue;
    // 当前个数
    private AtomicInteger curNum = new AtomicInteger(0);

    public Storehouse(int capacity) {
        this.capacity = capacity;
        this.blockingQueue = new ArrayBlockingQueue<Object>(capacity);
    }

    /**
     * 生产的方法
     *
     * @throws InterruptedException
     */
    public void produrce(int num) {
        while (num + curNum.get() > capacity) {
            System.out.println("【仓库已无法再生产：" + num + "个产品】" + "当前仓库产品数量：" + curNum.get());
        }

        System.out.println("【仓库还未满，生产：" + num + "个产品没有问题】" + "当前仓库产品数量：" + blockingQueue.size());
        for (int i = 0; i < num; i++) {
            blockingQueue.add(new Object());
            curNum.incrementAndGet();
        }

    }

    /**
     * 消费
     *
     * @param num
     * @throws InterruptedException
     */
    public void consumer(int num) {
        while (num > curNum.get()) {
            System.out.println("【仓库没有：" + num + "个产品可消费】" + "当前仓库产品数量：" + blockingQueue.size());
        }

        System.out.println("【仓库有：" + num + "个产品可消费】" + "当前仓库产品数量：" + blockingQueue.size());
        for (int i = 0; i < num; i++) {
            try {
                blockingQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            curNum.decrementAndGet();
        }

    }
}
