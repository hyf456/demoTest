package com.han.thread.communication;


import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: hanyf
 * @Description: 线程响应中断
 * @Date: 2018/11/8 19:34
 */
public class StopThread implements Runnable {
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            //线程执行具体逻辑
            System.out.println(Thread.currentThread().getName() + "运行中。。");
        }
        System.out.println(Thread.currentThread().getName() + "退出。。。");
    }

    public static void main(String[] args) throws Exception {
        //Thread thread = new Thread(new StopThread(), "thread A");
        //thread.start();
        //
        //System.out.println("main 线程正在运行");
        //TimeUnit.MILLISECONDS.sleep(10);
        ////但是如果抛出了 InterruptedException 异常，该标志就会被 JVM 重置为 false。
        //thread.interrupt();
        //executorService();
        piped();
    }

    /**
     *
     *功能描述 线程池 awaitTermination() 方法
     * 如果是用线程池来管理线程，可以使用以下方式来让主线程等待线程池中所有任务执行完毕:
     * @author hanyf
     * @date 2018/11/8
     * @param []
     * @return void
     */
    private static void executorService() throws Exception {
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(10);
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 5, 1, TimeUnit.MILLISECONDS, queue);
        poolExecutor.execute(() ->{
            System.out.println("running");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        poolExecutor.execute(() -> {
            System.out.println("running2");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        poolExecutor.shutdown();
        while (!poolExecutor.awaitTermination(1, TimeUnit.SECONDS)) {
            System.out.println("线程还在执行。。。");
        }
        System.out.println("main over");
    }

    public static void piped() throws IOException {
        //面向于字符PipedInputStream面向于字节
        PipedWriter writer = new PipedWriter();
        PipedReader reader = new PipedReader();

        //输入输出流建立建立连接
        writer.connect(reader);

        Thread t1 = new Thread(() ->{
            System.out.println("running");
            try {
                for (int i = 0; i < 10; i++) {
                    writer.write(i + "");
                    Thread.sleep(10);
                }
            } catch (Exception e) {

            } finally {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t2 = new Thread(() ->{
            System.out.println("running2");
            int msg = 0;
            try {
                while ((msg = reader.read()) != -1) {
                    System.out.println("msg = " + (char)msg);
                }
            } catch (Exception e) {

            }
        });
        t1.start();
        t2.start();

    }
}
