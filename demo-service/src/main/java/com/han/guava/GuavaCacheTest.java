package com.han.guava;

import java.util.concurrent.TimeUnit;

/**
 * @Author:
 * @Description:
 * @Date: 2018/5/16 16:50
 */
public class GuavaCacheTest {

    public static void main(String[] args) throws Exception {
        CacheDemo us = new CacheDemo();
        for(int i=0;i<20;i++) {
            System.out.println("结果" + us.getAndyName("c"));
            TimeUnit.SECONDS.sleep(1);

            TradeAccount a = us.getTradeAccountCache("a");
            System.out.println("结果" + a);
        }
    }
}
