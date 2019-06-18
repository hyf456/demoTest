package com.han.oom;

/**
 * @Author: hanyf
 * @Description: 模拟虚拟机oom
 * @Date: 2018/6/4 17:52
 */
public class JavaVMStackOOM {

    private void dontStop() {
        while (true) {}
    }

    public void stackLeakByThread() {
        while (true) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    dontStop();
                }
            });
            thread.start();
        }
    }

    public static void main(String[] args) {
        JavaVMStackOOM oom = new JavaVMStackOOM();
        oom.stackLeakByThread();
    }
}
