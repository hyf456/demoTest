package com.han.knowledge.ReentrantLock;

/**
 * Created by hp on 2017-04-26.
 */
public class Reader extends Thread {

    private Buffer buff;

    public Reader(Buffer buff) {
        this.buff = buff;
    }

    @Override
    public void run() {

        buff.read();//这里估计会一直阻塞

        System.out.println("读结束");

    }
}
