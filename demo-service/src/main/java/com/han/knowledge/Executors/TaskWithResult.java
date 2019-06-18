package com.han.knowledge.Executors;

import java.util.concurrent.Callable;

/**
 * 功能概要：缓冲线程池实例-submit运行
 * Created by hp on 2017-04-25.
 */
public class TaskWithResult implements Callable<String> {

    private int id;

    public TaskWithResult(int id) {
        this.id = id;
    }

    /**
     * 任务的具体过程，一旦任务传给ExecutorService的submit方法，则该方法自动在一个线程上执行。
     *
     * @return
     * @throws Exception
     */
    public String call() throws Exception {
        System.out.println("call()方法被自动调用,干活！！！             " + Thread.currentThread().getName());
        //一个模拟耗时的操作
        for (int i = 999999; i > 0; i--) ;
        return"call()方法被自动调用，任务的结果是：" + id + "    " + Thread.currentThread().getName();
    }
}
