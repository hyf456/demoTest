package com.han.knowledge.Singleton;

/**
 * Created by hp on 2017-06-15.
 */
public class Singleton3 {

    private static Singleton3 singleton3 = new Singleton3();

    private Singleton3 () {}

    public static Singleton3 getInstance3() {
        return singleton3;
    }
}
