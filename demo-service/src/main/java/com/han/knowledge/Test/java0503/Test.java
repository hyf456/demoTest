package com.han.knowledge.Test.java0503;

/**
 * Created by hp on 2017-05-03.
 */
public class Test {
    public void test(Bird bird){
        System.out.println(bird.getName() + "能够飞 " + bird.fly() + "米");
    }

    public static void main(String[] args) {
        Test test = new Test();
        Bird bird = new Bird() {
            @Override
            public int fly() {
                return 10000;
            }
            public String getName() {
                return "小鸟";
            }
        };
        test.test(bird);
        test.test(new Bird() {

            public int fly() {
                return 10000;
            }

            public String getName() {
                return "大雁";
            }
        });
    }
}
