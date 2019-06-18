package com.han.knowledge.Test.java0503;

/**
 * Created by hp on 2017-05-03.
 */
public class Out {
    private int age = 12;
    class In {
        private int age = 13;
        public void print() {
            int age = 14;
            System.out.println("局部变量：" + age);
            System.out.println("内部类变量：" + this.age);
            System.out.println("外部类变量：" + Out.this.age);
        }
    }

    public static void main(String[] args) {
        String a = "11";
        String b = a + "bb";
        System.out.println(a == b);

        StringBuffer d = new StringBuffer("dd");
        StringBuffer f = d.append("dd");
        System.out.println( d == f);
    }
}
