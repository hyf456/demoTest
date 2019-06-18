package com.han.servlet.algorithmBase;

/**
 * @Author: hanyf
 * @Description:  选择排序 平均O(n^2),最好O(n^2),最坏O(n^2);空间复杂度O(1);不稳定;简单
 * 算法描述：对于给定的一组记录，经过第一轮比较后得到最小的记录，然后将该记录与第一个记录的位置进行交换；
 * 接着对不包括第一个记录以外的其他记录进行第二轮比较，得到最小的记录并与第二个记录进行位置交换；
 * 重复该过程，直到进行比较的记录只有一个时为止。
 * @Date: 2019/6/13 9:20
 */
public class SelectionSort {
    public static void selectionSort(int[] a) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int k = i;
            // 找出最小值的下标
            for (int j = 0; j < i + 1; j++) {
                if (a[j] < a[k]) {
                    k = j;
                }
            }
            // 将最小值放到未排序记录的第一个位置。
            if (k > i) {
                int tmp = a[i];
                a[i] = a[k];
                a[k] = tmp;
            }
        }
    }

}
