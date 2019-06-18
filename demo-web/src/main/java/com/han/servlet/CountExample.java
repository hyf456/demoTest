package com.han.servlet;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: hanyf
 * @Description:
 * @Date: 2019/5/6 18:00
 */
@Slf4j
public class CountExample {

    /**
     * 请求总数
     */
    public static int clientTotal = 500;

    /**
     * 同时并发执行的线程数
     */
    public static int threadTotal = 20;

    public static AtomicInteger count = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(threadTotal);
        // 信号量，此处用于控制并发的线程数
        final Semaphore semaphore = new Semaphore(threadTotal);
        // 用于挂起当前线程，知道线程数达到之后才同时执行后续任务
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(threadTotal);
        // 闭锁，可实现技术器递减
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal; i++) {
            executorService.execute(() -> {
                try {
                    // 执行此方法用于获取执行许可，当总计未释放的许可数不超过200时，
                    // 允许通行，否则线程阻塞等待，知道获取到许可。
                    semaphore.acquire();
                    // 用来挂起当前线程，直至所有线程都到达cyclicBarrier状态再同时执行后续任务；
                    // 等待所有线程都调用过此函数才能继续后续动作
                    // 只等待2s，那么最后一个线程3秒才执行，则必定会超时
                    cyclicBarrier.await(2000, TimeUnit.MILLISECONDS);
                    Thread.sleep(10000);
                    log.info("semaphore count:{},线程名:{}", count, Thread.currentThread().getName());
                    add();
                    // 释放许可
                    semaphore.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // 闭锁减一
                countDownLatch.countDown();
            });
        }
        // 线程阻塞，知道闭锁值为9时，阻塞才释放，继续往下执行
        countDownLatch.await();
        executorService.shutdown();
        log.info("count:{}", count);
    }

    private static void add() {
        count.incrementAndGet();
    }
}
