package com.han;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author: hanyf
 * @Description: 如何判断一个元素在亿级数据中是否存在？
 * @Date: 2018/12/12 15:33
 */
public class HashMapTest {

    @Test
    public void hashMapTests() {
        long star = System.currentTimeMillis();

        Set<Integer> hashset = new HashSet<>(100);
        for (int i = 0; i < 10000000; i++) {
            hashset.add(i);
        }

        Assert.assertTrue(hashset.contains(1));
        Assert.assertTrue(hashset.contains(2));
        Assert.assertTrue(hashset.contains(3));

        long end = System.currentTimeMillis();
        System.out.println("执行时间：" + (end - star));
    }
}
