package com.han.guava.newcache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: hanyf
 * @Description: Guava Cache内存缓存使用实践-定时异步刷新及简单抽象封装
 * @Date: 2018/8/28 9:41
 */
public class GuavaCacheMethod {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * @Author: hanyf
     * @Description: 定时过期
     * @Date: 2018/8/28 9:42
     */
    public void simpleCache() {
        LoadingCache<String, Object> caches = CacheBuilder.newBuilder()
                .maximumSize(100)//缓存容量大小，达到容量上线，会进行缓存回收，回收最近没有使用或总体上很少使用的缓存项
                .expireAfterWrite(10, TimeUnit.MINUTES)//定义缓存过期时间，十分钟之后过期
                .build(new CacheLoader<String, Object>() {//当缓存不存在或已过期，调用load重新尽心缓存值的计算

                    @Override
                    public Object load(String key) throws Exception {
//                        return generateValueByKey(key);
                        return null;
                    }
                });
        try {
            System.out.println(caches.get("key-zorro"));
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author: hanyf
     * @Description: 定时刷新
     * @Date: 2018/8/28 14:52
     */
    public void cacheRefush() {
        LoadingCache<String, Object> caches = CacheBuilder.newBuilder()
                .maximumSize(100)
                .refreshAfterWrite(10, TimeUnit.MINUTES)//每隔十分钟自动刷新
                .build(new CacheLoader<String, Object>() {
                    @Override
                    public Object load(String s) throws Exception {
                        return null;
                    }
                });
        try {
            System.out.println(caches.get("key-zorro"));
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author: hanyf
     * @Description: 异步刷新
     * 如"定时刷新"的使用方法，解决了同一个key的缓存过期时会让多个线程阻塞的问题，只会让用来执行刷新缓存操作的一个用户线程会被阻塞。由此可以想到另一个问题，当缓存的key很多时，高并发条件下大量线程同时获取不同key对应的缓存，此时依然会造成大量线程阻塞，并且给数据库带来很大压力。这个问题的解决办法就是将刷新缓存值的任务交给后台线程，所有的用户请求线程均返回旧的缓存值，这样就不会有用户线程被阻塞了。
     *
     * 在上面的代码中，我们新建了一个线程池，用来执行缓存刷新任务。并且重写了CacheLoader的reload方法，在该方法中建立缓存刷新的任务并提交到线程池。
     * 注意此时缓存的刷新依然需要靠用户线程来驱动，只不过和"定时刷新"不同之处在于该用户线程触发刷新操作之后，会立马返回旧的缓存值。
     *
     * @Date: 2018/8/28 14:54
     */
    public void cacheAsyn() {
        ListeningExecutorService backgroundRefreshPools =
                MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(20));
        LoadingCache<String, Object> caches = CacheBuilder.newBuilder()
                .maximumSize(100)
                .refreshAfterWrite(10, TimeUnit.MINUTES)
                .build(new CacheLoader<String, Object>() {
                    @Override
                    public Object load(String key) throws Exception {
//                        return generateValueByKey(key);
                        return null;
                    }

                    @Override
                    public ListenableFuture<Object> reload(String key, Object oldValue) throws Exception {
                        return backgroundRefreshPools.submit(new Callable<Object>() {
                            @Override
                            public Object call() throws Exception {
//                                return generateValueByKey(key);
                                return null;
                            }
                        });
                    }
                });
        try {
            System.out.println(caches.get("key-zorro"));
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    /**
     * TIPS
     * 可以看到防缓存穿透和防用户线程阻塞都是依靠返回旧值来完成的。所以如果没有旧值，同样会全部阻塞，因此应视情况尽量在系统启动时将缓存内容加载到内存中。
     *
     * 在刷新缓存时，如果generateValueByKey方法出现异常或者返回了null，此时旧值不会更新。
     *
     * 题外话：在使用内存缓存时，切记拿到缓存值之后不要在业务代码中对缓存直接做修改，因为此时拿到的对象引用是指向缓存真正的内容的。
     * 如果需要直接在该对象上进行修改，则在获取到缓存值后拷贝一份副本，然后传递该副本，进行修改操作。（我曾经就犯过这个低级错误 - -！）
     */

    /**
     * @Author: hanyf
     * @Description:  缓存刷新
     * @Date: 2018/8/28 17:05
     */
    public void testCacheRefresh() throws InterruptedException {
        AtomicInteger counter = new AtomicInteger(0);
        CacheLoader<String, Long> cacheLoader = CacheLoader
                .from(k -> {
                    counter.incrementAndGet();
                    logger.info("创建 key :" + k);
                    return System.currentTimeMillis();
                });
        LoadingCache<String, Long> cache = CacheBuilder.newBuilder()
                .refreshAfterWrite(2, TimeUnit.SECONDS) // 2s后重新刷新
                .build(cacheLoader);


        Long result1 = cache.getUnchecked("guava");
        TimeUnit.SECONDS.sleep(3);
        Long result2 = cache.getUnchecked("guava");
        logger.info(result1.longValue() != result2.longValue() ? "是的" : "否");

    }
}
