package com.han.servlet.algorithmBase;

/**
 * @Author: hanyf
 * @Description: 冒泡排序  平均O(n^2),最好O(n),最坏O(n^2);空间复杂度O(1);稳定;简单
 * 算法描述：对于给定的n个记录，从第一个记录开始依次对相邻的两个记录进行比较，
 * 当前面的记录大于后面的记录时，交换位置，进行一轮比较和交换后，n个记录中的最大记录将位于第n位；
 * 然后对前（n-1）个记录进行第二轮比较；重复该过程直到进行比较的记录只剩下一个为止。
 * @Date: 2019/6/13 10:14
 */
public class bubbleSort {
    public static void bubbleSort(int[] a) {
        int n = a.length;
        int temp = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (a[j] < a[j + 1]) {
                    temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] a = {49, 38, 65, 97, 76, 13, 27, 50};
        bubbleSort(a);
        for (int j : a) {
            System.out.print(j + " ");
        }
    }
}
