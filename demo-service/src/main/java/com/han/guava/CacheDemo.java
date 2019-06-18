package com.han.guava;

import com.google.common.base.Ticker;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

/**
 * @Author:
 * @Description:
 * @Date: 2018/5/16 15:39
 */
public class CacheDemo {

    @Autowired
    public TradeAccountServiceImpl tradeAccountService;
    @Autowired
    private BookServiceImpl bookService;


    private final LoadingCache<String, TradeAccount> cache;
    private final LoadingCache<String, Book> bookCache;
    private final LoadingCache<String, TradeAccount> tradeAccountCache;


    public CacheDemo() {
        TradeAccountServiceImpl tradeAccountService = new TradeAccountServiceImpl();
        /**
         * @Author: hanyf
         * @Description: 在缓存条目加载到缓存中后，我们如何指定条目失效
         * @Date: 2018/5/16 15:54
         */
        cache =
                CacheBuilder.newBuilder()
                        //自动的使缓存条目在指定的时间后失效
                        .expireAfterWrite(5L, TimeUnit.MINUTES)
                        //指定了缓存的最大大小，当缓存的大小逼近到最大值时，缓存中一些最近很少使用到的条目将会被移除，不一定在缓存大小达到最大值甚至超过最大值才移除
                        .maximumSize(5000L)
                        //注册了一个RemovalListener监听器实例，它可以在缓存中的条目被移除后接收通知
                        .removalListener(new TradeAccountRemovalListener())
                        .ticker(Ticker.systemTicker())
                        //调用了build方法，传入了一个新的CacheLoader实例，当缓存中的key存在，value不存在时，这个实例将被用来重新获取TradeAccount对象。
                        .build(new CacheLoader<String, TradeAccount>() {
                            @Override
                            public TradeAccount load(String key) throws
                                    Exception {
//                                TradeAccount tradeAccount = newcache TradeAccount();
//                                tradeAccount.setId(key);
//                                return tradeAccount;
                                return tradeAccountService.getTradeAccountById(key);
                            }
                        });
        /**
         * @Author: hanyf
         * @Description: 怎么使缓存条目失效，基于上一次条目被访问后所经过的时间
         * @Date: 2018/5/16 15:54
         */
        bookCache = CacheBuilder.newBuilder()
                //调用expireAfterAccess方法，我们指定了缓存中的条目在上一次访问经过20分钟后失效。
                .expireAfterAccess(20L, TimeUnit.MINUTES)
                //不再明确的指定缓存的最大值， 而是通过调用softValues()方法， 让JVM虚拟机将缓存中的条目包装成软引用对象，以限制的缓存大小。如果内存空间不足，缓存中的条目将会被移除。需要注意的是，哪些软引用可以被垃圾回收 是由JVM内部进行的LRU计算所决定的。
                .softValues()
                //添加了一个类似的RemovalListener监听器，用于处理缓存中value值不存在的条目。
                .removalListener(new BookRemovalListener())
                .build(new CacheLoader<String, Book>() {
                    @Override
                    public Book load(String key) throws Exception {
                        return bookService.getBookByIsbn(key);
                    }
                });

        /**
         * @Author: hanyf
         * @Description: 如何自动的刷新LongCache缓存中的条目值
         * @Date: 2018/5/16 15:56
         */
        tradeAccountCache =
                CacheBuilder.newBuilder()
                        //调用concurrencyLevel方法，我们设置了并发更新操作的数量为10，如果不设置的话，默认值为4。
                        .concurrencyLevel(10)
                        //不再明确的移除缓存条目，而是通过refreshAfterWrite方法在给定的时间过去后，刷新缓存中的条目值，当缓存条目的值被调用并且已经超过了设置的时间，刷新缓存的触发器将处于活动状态。
                        .refreshAfterWrite(5L, TimeUnit.SECONDS)
                        //我们添加了纳秒级别的Ticker，以刷新那些符合条件的条目值。
                        .ticker(Ticker.systemTicker())
                        //通过调用build方法，我们指定了需要使用的Loader。
                        .build(new CacheLoader<String,
                                TradeAccount>() {
                            @Override
                            public TradeAccount load(String key)
                                    throws Exception {
                                return tradeAccountService.getTradeAccountById(key);
                            }
                        });
    }

    public TradeAccount getAndyName(String id) throws Exception {
        TradeAccount tradeAccount = cache.get(id);
        return tradeAccount;
    }

    public TradeAccount getTradeAccountCache(String id) throws Exception {
        TradeAccount tradeAccount = tradeAccountCache.get(id);
        return tradeAccount;
    }

}
