package com.han.knowledge.ProducerConsumer.Lock;

/**
 * Created by hp on 2017-04-26.
 */
public class ProducerThread extends Thread {

    // 每次生产的产品数量
    private int num;

    // 所在放置的仓库
    private Storehouse storehouse;

    // 构造函数，设置仓库
    public ProducerThread(Storehouse storehouse, int num) {
        this.storehouse = storehouse;
        this.num = num;
    }

    public void run() {
        try {
            storehouse.produrce(num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
