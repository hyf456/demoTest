package com.han.practise;

/**
 * @Author:
 * @Description: 测试
 * @Date: 2018/4/9 12:33
 */
public class Main {
    public static void main(String[] args) {

        Thread2 my = new Thread2();
        //同一个mt，但是在Thread中就不可以，如果用同一个实例化对象mt，就会出现异常
        new Thread(my, "C").start();
        new Thread(my, "D").start();
        new Thread(my, "E").start();

//        Thread1 mTh1=newcache Thread1("A");
//        Thread1 mTh2=mTh1;
//        mTh1.start();
//        mTh2.start();
    }
}
