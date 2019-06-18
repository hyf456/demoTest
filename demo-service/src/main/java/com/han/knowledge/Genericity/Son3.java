package com.han.knowledge.Genericity;

/**
 * 继承时不指定父类的泛型参数,会有警告信息：Father is a raw type.
 * References to generic type Father<T> should be
 * parameterized
 * Created by hp on 2017-04-10.
 */
public class Son3 extends Father{
    public Son3(Object data) {// 这个的data类型为Object
        super(data);
    }

    @Override
    public String toString() {
        return "Son3 [data=" + data + "]";
    }
}
