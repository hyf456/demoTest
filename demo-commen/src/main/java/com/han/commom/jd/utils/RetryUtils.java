package com.han.commom.jd.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.function.Function;

/**
 * @Author : wangjingwang
 * @Date : 2018/6/12 11:23
 * @Description :
 */
@Slf4j
public class RetryUtils {
    /**
     * 无间隔
     *
     * @param maxRetryTimes 重试次数（不包含第一次执行）
     * @param callable      执行体
     * @param <R>           返回值
     * @return
     * @throws Exception
     */
    public static void retry(int maxRetryTimes, Call callable) throws Exception {
        retry(maxRetryTimes, () -> {
            callable.call();
            return null;
        });
    }

    public static <R> R retry(int maxRetryTimes, Callable<R> callable) throws Exception {
        return retryFixedDelay(maxRetryTimes, -1, callable);
    }

    /**
     * 固定间隔
     *
     * @param maxRetryTimes 重试次数（不包含第一次执行）
     * @param delayMilli    每次重试间隔（固定间隔）
     * @param callable      执行体
     * @param <R>           返回值
     * @return
     * @throws Exception
     */
    public static void retryFixedDelay(int maxRetryTimes, long delayMilli, Call callable) throws Exception {
        retryFixedDelay(maxRetryTimes, delayMilli, () -> {
            callable.call();
            return null;
        });
    }

    public static <R> R retryFixedDelay(int maxRetryTimes, long delayMilli, Callable<R> callable) throws Exception {
        return doRetry(maxRetryTimes, callable, retryTimes -> delayMilli);
    }

    /**
     * 指数间隔（即每次重试间隔按指数增长）
     *
     * @param maxRetryTimes 重试次数（不包含第一次执行）
     * @param baseDelayMilli    基础间隔
     * @param maxDelayMilli 最大间隔（当指数计算结果超过最大间隔时，使用最大间隔）
     * @param baseNumber    指数的底数
     * @param callable      执行体
     * @param <R>           返回值
     * @return
     * @throws Exception
     */
    public static void retryExponentialDelay(int maxRetryTimes, long baseDelayMilli, long maxDelayMilli, int baseNumber, Call callable) throws Exception {
        retryExponentialDelay(maxRetryTimes, baseDelayMilli, maxDelayMilli, baseNumber, () -> {
            callable.call();
            return null;
        });
    }

    public static <R> R retryExponentialDelay(int maxRetryTimes, long baseDelayMilli, long maxDelayMilli, int baseNumber, Callable<R> callable) throws Exception {
        return doRetry(maxRetryTimes, callable, retryTimes -> getExponentialDelay(retryTimes, baseDelayMilli, maxDelayMilli, baseNumber));
    }

    /**
     * 重试实现
     * @param maxRetryTimes 重试次数（不包含第一次执行）
     * @param callable 执行体
     * @param getDelay 获取重试间隔
     * @param <R>
     * @return
     * @throws Exception
     */
    private static <R> R doRetry(int maxRetryTimes, Callable<R> callable, Function<Integer, Long> getDelay) throws Exception {
        if (null == callable) {
            return null;
        }
        if (maxRetryTimes < 0) {
            maxRetryTimes = 0;
        }

        int runTimes = 0;
        R result = null;
        while (true) {
            runTimes++;
            try {
                result = callable.call();
                break;
            } catch (Exception e) {
                if (runTimes > maxRetryTimes) {
                    throw e;
                }
                log.warn("RetryUtils.retry-{}, error: {} ", runTimes, e.getMessage());
            }
            long delay = getDelay.apply(runTimes);
            if (delay > 0) {
                sleep(delay);
            }
        }
        return result;
    }

    /**
     * 获取指数重试间隔
     * @param retryTimes
     * @param baseDelayMilli
     * @param maxDelayMilli
     * @param baseNumber
     * @return
     */
    private static long getExponentialDelay(int retryTimes, long baseDelayMilli, long maxDelayMilli, int baseNumber) {
        if (baseNumber <= 1 || baseDelayMilli <= 0) {
            return baseDelayMilli;
        }
        int exponent = retryTimes - 1;

        long delay = Math.round(baseDelayMilli * Math.pow(baseNumber, exponent));

        if (delay > maxDelayMilli) {
            delay = maxDelayMilli;
        }
        return delay;
    }

    private static void sleep(long delayMilli) {
        try {
            Thread.sleep(delayMilli);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @FunctionalInterface
    public interface Call {
        void call() throws Exception;
    }

}
