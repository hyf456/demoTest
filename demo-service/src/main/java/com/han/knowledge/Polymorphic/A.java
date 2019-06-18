package com.han.knowledge.Polymorphic;

/**
 * Created by hp on 2017-06-14.
 */
public class A {
    public String show(D obj) {
        return ("A and D");
    }

    public String show(A obj) {
        return ("A and A");
    }
}
