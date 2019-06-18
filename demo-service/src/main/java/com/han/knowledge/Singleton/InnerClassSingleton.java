package com.han.knowledge.Singleton;

/**
 * Created by hp on 2017-06-15.
 */
public class InnerClassSingleton {

    public static Singleton getInstance() {
        return Singleton.singleton;
    }

    private static class Singleton {
        protected static Singleton singleton = new Singleton();
    }
}
