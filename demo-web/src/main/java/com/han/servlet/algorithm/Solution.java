package com.han.servlet.algorithm;

/**
 * @Author: hanyf
 * @Description: 奇数在前偶数在后
 * @Date: 2019/5/14 15:39
 */
public class Solution {

    /**
     * 将数组a中奇数放在前面，偶数放在后面
     * @param a
     */
    private void reOrderArray(int a[]) {
        int len = a.length;
        //判断数组长度为0则返回
        if (len <= 0) {
            return;
        }
        //设置两个指针，一个指向头部，一个指向尾部
        int front = 0, end = len - 1;
        while (front < end) {
            //从前往后找偶数
            while (front < end && (a[front] & 1) == 1) {
                front++;
            }
            //从后往前找奇数
            while (end >= 0 && (a[end] & 1) == 0) {
                end++;
            }
            if (front < end) {
                //将技术往前挪，偶数往后挪
                int swap = a[front];
                a[front] = a[end];
                a[end] = swap;
            }
        }

    }

    public static void main(String[] args) {
        int a;
        int arr[] = {1,8,3,6,5,6,3,4};

        if (arr == null || arr.length <= 1) {
            return ;
        }
        int index = 0;
        for (int i = 0; i < arr.length; ++i) {
            if ((arr[i]&1) != 0) {
                int tmp = arr[i];
                arr[i] = arr[index];
                arr[index++] = tmp;
            }
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }


    }
}
