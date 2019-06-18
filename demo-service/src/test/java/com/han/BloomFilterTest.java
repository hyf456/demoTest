// package com.han;
//
// import com.han.bloom.filters.BloomFilters;
// import org.testng.annotations.Test;
//
// /**
//  * @Author: hanyf
//  * @Description: 布隆过滤
//  * @Date: 2018/12/13 15:01
//  */
// public class BloomFilterTest {
//
//     @Test
//     public void bloomFilterTest() {
//         long star = System.currentTimeMillis();
//         BloomFilters bloomFilters = new BloomFilters(10000000);
//         for (int i = 0; i < 10000000; i++) {
//             bloomFilters.add(i + "");
//         }
//
//         //Assert.assertTrue(bloomFilters.check(1 + ""));
//         //Assert.assertTrue(bloomFilters.check(2 + ""));
//         //Assert.assertTrue(bloomFilters.check(3 + ""));
//         //Assert.assertTrue(bloomFilters.check(999999 + ""));
//         //Assert.assertTrue(bloomFilters.check(400230340 + ""));
//         long end = System.currentTimeMillis();
//         System.out.println("执行时间：" + (end - star));
//     }
// }
