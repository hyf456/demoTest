package com.han.knowledge.ExecutorService;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by hp on 2017-04-25.
 */
public class TestCachedThreadPool {

    public static void cachedThreadPool() {
        System.out.println("Main: Starting at: "+ new Date());
        ExecutorService exec = Executors.newCachedThreadPool();   //创建一个缓冲池，缓冲池容量大小为Integer.MAX_VALUE
        for(int i = 0; i < 10; i++) {
            exec.execute(new CachedThreadPool(String.valueOf(i)));
        }
        exec.shutdown();  //执行到此处并不会马上关闭线程池,但之后不能再往线程池中加线程，否则会报错
        System.out.println("Main: Finished all threads at"+ new Date());
    }

    public static void testFixThreadPool() {
        System.out.println("Main Thread: Starting at: "+ new Date());
        ExecutorService exec = Executors.newFixedThreadPool(5);
        for(int i = 0; i < 10; i++) {
            exec.execute(new CachedThreadPool(String.valueOf(i)));
        }
        exec.shutdown();  //执行到此处并不会马上关闭线程池
        System.out.println("Main Thread: Finished at:"+ new Date());
    }

    public static void testSingleThreadPool() {
        System.out.println("Main Thread: Starting at: "+ new Date());
        ExecutorService exec = Executors.newSingleThreadExecutor();   //创建大小为1的固定线程池
        for(int i = 0; i < 10; i++) {
            exec.execute(new CachedThreadPool(String.valueOf(i)));
        }
        exec.shutdown();  //执行到此处并不会马上关闭线程池
        System.out.println("Main Thread: Finished at:"+ new Date());
    }

    public static void testScheduledThreadPool() {
        System.out.println("Main Thread: Starting at: "+ new Date());
        ScheduledThreadPoolExecutor exec = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(10);   //创建大小为10的线程池
        for(int i = 0; i < 10; i++) {
            exec.schedule(new CachedThreadPool(String.valueOf(i)), 10, TimeUnit.SECONDS);//延迟10秒执行
        }
        exec.shutdown();  //执行到此处并不会马上关闭线程池
        while(!exec.isTerminated()){
            //wait for all tasks to finish
        }
        System.out.println("Main Thread: Finished at:"+ new Date());
    }

    public static void main(String[] args) {
        TestCachedThreadPool testCachedThreadPool = new TestCachedThreadPool();
//        testCachedThreadPool.cachedThreadPool();
//        testCachedThreadPool.testFixThreadPool();
//        testCachedThreadPool.testSingleThreadPool();
        testCachedThreadPool.testScheduledThreadPool();
    }
}
