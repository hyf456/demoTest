package com.han.knowledge.Singleton;

/**
 * Created by hp on 2017-06-15.
 */
public class Singleton1 {

    private static Singleton1 singleton1;

    private Singleton1() {};

    public static Singleton1 getInstance() {
        if (singleton1 == null) {
            singleton1 = new Singleton1();
        }
        return singleton1;
    }
}
