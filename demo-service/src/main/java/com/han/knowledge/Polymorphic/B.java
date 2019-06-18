package com.han.knowledge.Polymorphic;

/**
 * Created by hp on 2017-06-14.
 */
public class B extends A {
    public String show(B obj){
        return ("B and B");
    }

    public String show(A obj){
        return ("B and A");
    }
}
