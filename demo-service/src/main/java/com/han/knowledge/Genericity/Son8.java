package com.han.knowledge.Genericity;

/**
 * 父类和子类的泛型参数具有关系
 * Created by hp on 2017-04-10.
 */
public class Son8<T, E extends T> extends Father<T> {
    E otherData;

    public Son8(T data, E otherData) {
        super(data);
        this.otherData = otherData;
    }

    @Override
    public String toString() {
        return "Son8 [otherData=" + otherData + ", data=" + data + "]";
    }
}
