package com.han.knowledge.JMX;

/**
 * Created by hp on 2017-04-10.
 */
public interface HelloMBean {

    public void sayHello();
    public int add(int x, int y);

    public String getName();

    public int getCacheSize();
    public void setCacheSize(int size);
}
