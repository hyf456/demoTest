package com.han.servlet.algorithm;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Author: hanyf
 * @Description: 判断一个数是否为快乐数字 19 就是快乐数字 11 就不是快乐数字
 * 19
 * 1*1+9*9=82
 * 8*8+2*2=68
 * 6*6+8*8=100
 * 1*1+0*0+0*0=1
 *
 * 11
 * 1*1+1*1=2
 * 2*2=4
 * 4*4=16
 * 1*1+6*6=37
 * 3*3+7*7=58
 * 5*5+8*8=89
 * 8*8+9*9=145
 * 1*1+4*4+5*5=42
 * 4*4+2*2=20
 * 2*2+0*0=2
 *
 * 这里结果 1*1+1*1=2 和 2*2+0*0=2 重复，所以不是快乐数字
 * @Date: 2019/6/4 11:11
 */
public class HappyNum {

    /**
     * 功能描述 判断一个数字是否为快乐数字
     * @author hanyf
     * @date 2019/6/4 13:43
     * @param number
     * @return boolean
     */
    public boolean isHappy(int number) {
        Set<Integer> set = new HashSet<>(30);
        while (number != 1) {
            int sum = 0;
            while (number > 0) {
                // 计算当前值的每位数的平方 相加的和 在放入set中，如果存在相同的就认为不是 happy数字
                sum += (number % 10) * (number % 10);
                number = number / 10;
            }
            if (set.contains(sum)) {
                return false;
            } else {
                set.add(sum);
            }
            number = sum;
        }
        return true;
    }

    public static void main(String[] args) {
        int num = 345;
        int i = num % 10;
        int i1 = num / 10;
        int i2 = i1 / 10;
        System.out.println(i);
        System.out.println(i1);
        System.out.println(i2);

        StringBuffer s = new StringBuffer();
        System.out.println(s.toString());
        Map<String, Integer> map = new HashMap<>();
        map.put("key1", 1);
        map.put("key2", 2);
        map.put("key3", 3);
        map.put("key4", 4);
        map.put("key5", 5);
        map.put("key5", 6);

        map.forEach((k, v) -> {s.append(k).append(v);});

        System.out.println(s.toString());

        BigDecimal bigDecimal = toBigDecimal(toBigDecimal(9).divide(toBigDecimal(4)));
        System.out.println(bigDecimal);
    }

    public static BigDecimal toBigDecimal(Object obj) {
        if (obj == null) {
            return BigDecimal.ZERO;
        }
        try {
            BigDecimal bd = new BigDecimal(obj.toString());
            // 设置小数位数，第一个变量是小数位数，第二个变量是取舍方法(四舍五入)
            return bd.setScale(2, BigDecimal.ROUND_HALF_UP);

        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }
}
