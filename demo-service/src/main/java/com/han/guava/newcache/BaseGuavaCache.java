package com.han.guava.newcache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author: hanyf
 * @Description: 利用guava实现的内存缓存。缓存加载之后永不过期，后台线程定时刷新缓存值。刷新失败时将继续返回旧缓存。
 * 在调用getValue之前，需要设置 refreshDuration， refreshTimeunit， maxSize 三个参数
 * 后台刷新线程池为该系统中所有子类共享，大小为20.
 * @Date: 2018/8/28 15:46
 */
public abstract class BaseGuavaCache <K, V> {
    private Logger logger = LoggerFactory.getLogger(getClass());

    //缓存自动刷新周期
    protected int refreshDuration = 10;
    //缓存刷新周期时间格式
    protected TimeUnit refreshTimeunit = TimeUnit.MINUTES;
    //缓存过期时间（可选择）
    protected int expireDuration = -1;
    //缓存刷新周期时间格式
    protected TimeUnit expireTimeunit = TimeUnit.HOURS;
    //缓存最大容量
    protected int maxSize = 4;
    //数据刷新线程池


    protected static ListeningExecutorService refreshPool = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(20));

    private LoadingCache<K, V> cache = null;

    /**
     * @Author: hanyf
     * @Description: 用于初始换缓存值（某些场景下使用，例如系统启动检测缓存加载是否正常）
     * @Date: 2018/8/28 15:52
     */
    public abstract void loadValueWhenStarted();

    /**
     * @Author: hanyf
     * @Description: 定义缓存值的计算方法
     * 新值计算失败时抛出异常，get操作时将继续返回旧的缓存
     * @Date: 2018/8/28 15:53
     */
    protected abstract V getValueWhenExpired(K key) throws Exception;

    /**
     * @Author: hanyf
     * @Description: 从cache中拿出数据操作
     * @Date: 2018/8/28 15:54
     */
    public V getValue(K key) throws Exception {
        try {
            return getCache().get(key);
        } catch (Exception e) {
            logger.error("从内存缓存中获取内容时发生异常，key:" + key, e);
            throw e;
        }
    }

    public V getValueOrDefault(K key, V defaultValue) {
        try {
            return getCache().get(key);
        } catch (Exception e) {
            logger.error("从内存缓存中获取内容时发生异常，key:" + key, e);
            return defaultValue;
        }
    }

    /**
     * @Author: hanyf
     * @Description: 设置基本属性
     * @Date: 2018/8/28 15:58
     */
    public BaseGuavaCache<K, V> setRefreshDuration(int refreshDuration) {
        this.refreshDuration = refreshDuration;
        return this;
    }

    public BaseGuavaCache<K, V> setRefreshTimeUnit(TimeUnit refreshTImeunit) {
        this.refreshTimeunit = refreshTimeunit;
        return this;
    }

    public BaseGuavaCache<K, V> setExpireDuration( int expireDuration){
        this.expireDuration = expireDuration;
        return this;
    }

    public BaseGuavaCache<K, V> setExpireTimeUnit(TimeUnit expireTimeunit){
        this.expireTimeunit = expireTimeunit;
        return this;
    }

    public BaseGuavaCache<K, V> setMaxSize( int maxSize ){
        this.maxSize = maxSize;
        return this;
    }

    public void clearAll(){
        this.getCache().invalidateAll();
    }

    /**
     * @Author: hanyf
     * @Description: 获取cache实例
     * @Date: 2018/8/28 16:01
     */
    private LoadingCache<K, V> getCache() {
        if (null == cache) {
            synchronized (this) {
                if (null == cache) {
                    CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder().maximumSize(maxSize);
                    if (refreshDuration > 0) {
                        cacheBuilder = cacheBuilder.refreshAfterWrite(refreshDuration, refreshTimeunit);
                    }
                    if (expireDuration > 0) {
                        cacheBuilder = cacheBuilder.expireAfterWrite(expireDuration, expireTimeunit);
                    }
                    cache = cacheBuilder.build(new CacheLoader<K, V>() {

                        @Override
                        public V load(K key) throws Exception {
                            return getValueWhenExpired(key);
                        }

                        @Override
                        public ListenableFuture<V> reload(K key, V oldValue) throws Exception {
                            return refreshPool.submit(() -> getValueWhenExpired(key));
                        }
                    });
                }
            }
        }
        return cache;
    }

    @Override
    public String toString() {
        return "GuavaCache";
    }

}
