package com.han.commom.util;

import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description 线程池工具类
 * @Date 2019/7/30 11:38
 * @Author hanyf
 */
public class ThreadPoolUtils {

	/*
	 * 池中所保存的线程数，包括空闲线程。
	 */
	@Value("threadPoolUtils.corePoolSize:200")
	private static int corePoolSize;
	private static String corePoolSizeKey = "threadPool.corePoolSize";
	/*
	 * 池中允许的最大线程数(采用LinkedBlockingQueue时没有作用)。
	 */
	@Value("threadPoolUtils.maximumPoolSize:500")
	private static int maximumPoolSize;
	private static String maximumPoolSizeKey = "threadPool.maximumPoolSize";
	/*
	 * 当线程数大于核心时，此为终止前多余的空闲线程等待新任务的最长时间，线程池维护线程所允许的空闲时间。
	 */
	@Value("threadPoolUtils.keepAliveTime:20")
	private static int keepAliveTime;
	private static String keepAliveTimeKey = "threadPool.keepAliveTime";

	private static volatile  ThreadPoolExecutor threadPool;
	/**
	 * 线程池单例
	 * @return
	 */
	private static ThreadPoolExecutor threadPoolInstance(){
		if(threadPool != null){
			return threadPool;
		}else{
			synchronized (ThreadPoolUtils.class){
				if(threadPool == null){
					corePoolSize = Integer.valueOf(corePoolSize);
					maximumPoolSize = Integer.valueOf(maximumPoolSize);
					keepAliveTime = Integer.valueOf(keepAliveTime);
					threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS,
							new ArrayBlockingQueue<Runnable>(maximumPoolSize) ,new ThreadPoolExecutor.DiscardOldestPolicy() );
				}
			}

		}
		return threadPool;
	}
	/**
	 * 通过线程池执行一个线程
	 * @param rb
	 */
	public static void execute(Runnable rb){
		threadPoolInstance().execute(rb);
	}

	/**
	 *
	 *功能描述  获取线程池
	 * @author hanyf
	 * @date 2018/11/16
	 * @param
	 * @return java.util.concurrent.ThreadPoolExecutor
	 */
	public static ThreadPoolExecutor getThreadPoolExecutor() {
		ThreadPoolExecutor poolExecutor = threadPoolInstance();
		return poolExecutor;
	}
}
