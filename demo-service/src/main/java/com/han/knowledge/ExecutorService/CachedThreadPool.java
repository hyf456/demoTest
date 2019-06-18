package com.han.knowledge.ExecutorService;

import java.util.Date;
/**
 * Created by hp on 2017-04-25.
 * 功能概要：缓冲线程池实例-execute运行
 */
public class CachedThreadPool implements Runnable {

    private String name;
    public CachedThreadPool(String name) {
        this.name = "thread"+name;
    }
    @Override
    public void run() {
        System.out.println( name +" Start. Time = "+new Date());
        processCommand();
        System.out.println( name +" End. Time = "+new Date());
    }
    private void processCommand() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    public String toString(){
        return this.name;
    }
}
