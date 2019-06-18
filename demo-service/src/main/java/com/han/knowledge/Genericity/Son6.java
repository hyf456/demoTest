package com.han.knowledge.Genericity;

/**
 * 子类的泛型参数是Integer 这个是我无意使用的，当然真实项目是绝对不允许这样使用的，一般泛型参数命名都是单个大写字母
 * 这里使用只是来说明万一泛型参数和一个类名相同了，别糊涂了（相同了都是来故意迷糊人的）
 * 这里的Integer 不是java.lang.Integer 它只是一个泛型参数名称 ，恰好相同，跟Son1是没有区别的
 * 它出现这里会把类中所有的Integer(java.lang.Integer) 都替换成 泛型参数
 * 警告提示：The type parameter Integer is hiding the type Integer
 * 所以传给父类的Integer，也不是java.lang.Integer，也只是一个类型参数
 * Created by hp on 2017-04-10.
 */
public class Son6<Integer> extends Father<Integer> {
    Integer otherData;// 它是什么类型呢？java.lang.Integer？NONONO 只能说不确定！
    public Son6(Integer data, Integer otherData) {
        super(data);
        this.otherData = otherData;
    }

    @Override
    public String toString() {
        return "Son6 [otherData=" + otherData + ", data=" + data + "]";
    }
}
