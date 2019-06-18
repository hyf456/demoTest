package com.han.knowledge.InternalStorage;

/**
 * Created by hp on 2017-06-09.
 */
public final class SetCheck {

    private int a = 0;
    private long b = 0;

    void set() {
        a = 1;
        b = -1;
    }

    boolean check() {
        return ((b == 0) ||
                (b == -1 && a == 1));
    }

    public static void main(String[] args) {
        SetCheck setCheck = new SetCheck();
        boolean check = setCheck.check();
        System.out.println(check);
    }

}
