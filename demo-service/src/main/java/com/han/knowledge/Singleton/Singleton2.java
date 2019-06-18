package com.han.knowledge.Singleton;

/**
 * Created by hp on 2017-06-15.
 */
public class Singleton2 {

    private Singleton2 () {}

    public static Singleton2 getInstance() {
        return SingletonInstance.singleton2;
    }

    private static class SingletonInstance{
        static Singleton2 singleton2 = new Singleton2();
    }
}
