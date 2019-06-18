package com.han.knowledge.ReentrantLock;

/**
 * Created by hp on 2017-04-26.
 */
public class Writer extends Thread {

    private Buffer buff;

    public Writer(Buffer buff) {
        this.buff = buff;
    }

    @Override
    public void run() {
        buff.write();
    }
}
