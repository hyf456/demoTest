package com.han.knowledge.Genericity;

/**
 * 最正常的继承，子类的泛型参数和父类的参数是一致的
 * Created by hp on 2017-04-10.
 */
public class Son1<T> extends Father<T> {
    public Son1(T data) {
        super(data);
    }

    @Override
    public String toString() {
        return "Son1 [data=" + data + "]";
    }
}
