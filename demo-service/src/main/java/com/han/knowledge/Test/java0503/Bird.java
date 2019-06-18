package com.han.knowledge.Test.java0503;

/**
 * Created by hp on 2017-05-03.
 */
public abstract class Bird {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract int fly();
}
