package com.han.knowledge.ThreadAll.Thread;

/**
 * Created by hp on 2017-02-17.
 */
public class TestA {

    public void test(){
        SourceA s = new SourceA();
        TestThread tt = new TestThread(s);
        TestRunnable tr = new TestRunnable(s);
        Thread t = new Thread(tr);
        System.out.println("调用线程1");
        tt.start();
        System.out.println("调用线程2");
        t.start();
    }

    public static void main(String[] args) {
        TestA t = new TestA();
        t.test();
    }
}
