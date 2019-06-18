package com.han.knowledge.Singleton;

/**
 * Created by hp on 2017-06-15.
 *双重锁
 */
public class SynchronizedSingleton {

    private static SynchronizedSingleton synchronizedSingleton;

    private SynchronizedSingleton() {};

    public static  SynchronizedSingleton getInstance() {
        if (synchronizedSingleton == null) {
            synchronized (SynchronizedSingleton.class) {
                if (synchronizedSingleton == null) {
                    synchronizedSingleton = new SynchronizedSingleton();
                }
            }
        }
        return synchronizedSingleton;
    }
}
