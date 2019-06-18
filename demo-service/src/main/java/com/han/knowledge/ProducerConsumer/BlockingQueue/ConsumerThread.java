package com.han.knowledge.ProducerConsumer.BlockingQueue;

/**
 * Created by hp on 2017-04-26.
 */
public class ConsumerThread extends Thread {

    // 每次生产的产品数量
    private int num;

    // 所在放置的仓库
    private Storehouse storehouse;

    // 构造函数，设置仓库
    public ConsumerThread(Storehouse storehouse, int num) {
        this.storehouse = storehouse;
        this.num = num;
    }

    public void run() {
        storehouse.consumer(num);
    }
}
