package com.han.knowledge.ProducerConsumer.WaitNotify;

import java.util.LinkedList;
import java.util.List;

/**
 *  1、生产者仅仅在仓储未满时候生产，仓满则停止生产。
 2、消费者仅仅在仓储有产品时候才能消费，仓空则等待。
 3、当消费者发现仓储没产品可消费时候会通知生产者生产。
 4、生产者在生产出可消费产品时候，应该通知等待的消费者去消费。
 * Created by hp on 2017-04-26.
 */
public class Storehouse {

    // 仓库的容量
    private int capacity;
    // object当成是生产的商品
    private List<Object> list = new LinkedList<Object>();

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
        // 同步方法
        synchronized (list) {
            // 仓库还未满，且再生产num个产品不会超过仓库容量时可以生产产品
            while (list.size() + num > this.capacity) {
                // 仓库已满，或者放不下
                System.out.println("【仓库已无法再生产：" + num + "个产品】" + "当前仓库产品数量：" + list.size());
                list.wait();

            }

            System.out.println("【仓库还未满，生产：" + num + "个产品没有问题】" + "当前仓库产品数量：" + list.size());
            for (int i = 0; i < num; i++) {
                list.add(new Object());
            }
            list.notifyAll();
        }
    }

    /**
     * 消费
     *
     * @param num
     * @throws InterruptedException
     */
    public void consumer(int num) throws InterruptedException {
        // 同步方法
        synchronized (list) {
            // 仓库有没有num个产品可消费
            while (list.size() < num) {
                System.out.println("【仓库没有：" + num + "个产品可消费】" + "当前仓库产品数量：" + list.size());
                list.wait();
            }
            System.out.println("【仓库有：" + num + "个产品可消费】" + "当前仓库产品数量：" + list.size());
            for (int i = 0; i < num; i++) {
                list.remove(0);
            }
            list.notifyAll();
        }
    }
}
