package com.han.architect.queue;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @Author: hanyf
 * @Description: 利用延迟队列，来书写一个具有过期key效果的简单缓存  缓存使用ConcurrentHashMap实现
 * @Date: 2020/6/17 18:13
 */
public class Cache<K, V> {

    //模拟装载缓存数据
    public ConcurrentHashMap<K, V> map = new ConcurrentHashMap<>();
    //缓存即将要过期的key们
    public DelayQueue<DelayedItem<K>> queue = new DelayQueue<>();

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        int cacheNumber = 10;
        int liveTime = 0;
        Cache<String, Integer> cache = new Cache<>();

        for (int i = 0; i < cacheNumber; i++) {
            liveTime = random.nextInt(3000);
            System.out.println(i + "  " + liveTime);
            cache.put(i + "", i, random.nextInt(liveTime));

            if (random.nextInt(cacheNumber) > 7) {
                liveTime = random.nextInt(3000);
                System.out.println(i + "  " + liveTime);
                cache.put(i + "", i, random.nextInt(liveTime));
            }
        }

        Thread.sleep(3000);
        System.out.println("--------------");
    }


    /**
     * 向缓存里面添加元素  可以指定key的存活时间
     *
     * @param k
     * @param v
     * @param liveTime
     */
    public void put(K k, V v, long liveTime) {
        V v2 = map.put(k, v);
        DelayedItem<K> tmpItem = new DelayedItem<>(k, liveTime);

        //把旧的移除掉 若存在旧的话
        if (v2 != null) {
            queue.remove(tmpItem);
        }
        queue.put(tmpItem);
    }

    //创建缓存对象的时候 开启一个守护线程 一直不停的去检查 阻塞队列里面是否有元素需要过期了移出来
    public Cache() {
        Thread t = new Thread(() -> {
            while (true) {
                DelayedItem<K> delayedItem = queue.poll(); //阻塞
                if (delayedItem != null) {
                    map.remove(delayedItem.getT());
                    System.out.println(System.nanoTime() + " remove " + delayedItem.getT() + " from cache");
                }
                try {
                    Thread.sleep(300);
                } catch (Exception e) {
                }
            }
        });
        t.setDaemon(true); //一定需要是守护线程
        t.start();
    }

}


class DelayedItem<T> implements Delayed {

    private T t;
    private long liveTime;
    private long removeTime;

    public DelayedItem(T t, long liveTime) {
        this.setT(t);
        this.liveTime = liveTime;
        this.removeTime = TimeUnit.NANOSECONDS.convert(liveTime, TimeUnit.NANOSECONDS) + System.nanoTime();
    }

    @Override
    public int compareTo(Delayed o) {
        if (o == null) return 1;
        if (o == this) return 0;
        if (o instanceof DelayedItem) {
            DelayedItem<T> tmpDelayedItem = (DelayedItem<T>) o;
            if (liveTime > tmpDelayedItem.liveTime) {
                return 1;
            } else if (liveTime == tmpDelayedItem.liveTime) {
                return 0;
            } else {
                return -1;
            }
        }
        //按照getDelay来比较即可  因为有可能传进俩的对象  并不是DelayedItem对象，而是别的Delayed对象
        long diff = getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
        return diff > 0 ? 1 : diff == 0 ? 0 : -1;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(removeTime - System.nanoTime(), unit);
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    //必须重写啊  因为需要根据key移除 底层依赖hashCode和equals
    @Override
    public int hashCode() {
        return t.hashCode();
    }

    //必须重写啊  因为需要根据key移除 底层依赖hashCode和equals
    @Override
    public boolean equals(Object object) {
        if (object instanceof DelayedItem) {
            return object.hashCode() == hashCode() ? true : false;
        }
        return false;
    }

}
/*输出：
0  2039
1  782
1  2354
2  1052
3  1078
4  2816
5  200
6  112
7  563
8  1383
8  900
9  2760
16353215371312 remove 6 from cache
16353515890864 remove 7 from cache
16353820262116 remove 5 from cache
16354120636676 remove 3 from cache
16354421318587 remove 8 from cache
16354722121324 remove 2 from cache
16355023081512 remove 1 from cache
16355323876697 remove 0 from cache
16355623834030 remove 4 from cache
16355924335835 remove 9 from cache*/

