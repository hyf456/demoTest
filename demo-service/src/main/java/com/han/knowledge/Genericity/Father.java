package com.han.knowledge.Genericity;

/**
 * Created by hp on 2017-04-10.
 */
public class Father<T> {
    T data;
    public Father(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Father [data=" + data + "]";
    }
}
