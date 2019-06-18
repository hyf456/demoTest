package com.han.knowledge.ProducerConsumer.Lock;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by hp on 2017-04-26.
 */
public class Storehouse {

    // 仓库的容量
    private int capacity;
    // object当成是生产的商品
    private List<Object> list = new LinkedList<Object>();

    // 锁
    private final Lock lock = new ReentrantLock();

    // 仓库满的条件变量
    private final Condition full = lock.newCondition();

    // 仓库空的条件变量
    private final Condition empty = lock.newCondition();

    public Storehouse(int capacity) {
        this.capacity = capacity;
        System.out.println("当前仓库产品数量：" + list.size());
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * 生产的方法
     *
     * @throws InterruptedException
     */
    public void produrce(int num) throws InterruptedException {
        try {
            lock.lock();
            // 仓库还未满，且再生产num个产品不会超过仓库容量时可以生产产品
            while (list.size() + num > this.capacity) {
                // 仓库已满，或者放不下
                System.out.println("【仓库已无法再生产：" + num + "个产品】" + "当前仓库产品数量：" + list.size());
                empty.await();
            }
            System.out.println("【仓库还未满，生产：" + num + "个产品没有问题】" + "当前仓库产品数量：" + list.size());
            for (int i = 0; i < num; i++) {
                list.add(new Object());
            }
            full.signalAll();
            empty.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void consumer(int num) throws InterruptedException {
        try {
            lock.lock();
            // 仓库有没有num个产品可消费
            while (list.size() < num) {
                System.out.println("【仓库没有：" + num + "个产品可消费】" + "当前仓库产品数量：" + list.size());
                full.await();
            }
            System.out.println("【仓库有：" + num + "个产品可消费】" + "当前仓库产品数量：" + list.size());
            for (int i = 0; i < num; i++) {
                list.remove(0);
            }
            empty.signalAll();
            full.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
