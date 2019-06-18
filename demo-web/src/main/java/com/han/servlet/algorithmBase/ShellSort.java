package com.han.servlet.algorithmBase;

/**
 * @Author: hanyf
 * @Description: 希尔排序  平均O(nlogn),最坏O(nlogn);空间复杂度O(1);不稳定;较复杂
 * 算法描述：先将待排序序列的数组元素分成多个子序列，使得每个子序列的元素个数相对较少，
 * 然后对各个子序列分别进行直接插入排序，待整个待排序序列“基本有序”后，再对所有元素进行一次直接插入排序。
 * @Date: 2019/6/13 9:57
 */
public class ShellSort {

    public static void shellSort(int[] a) {
        int n = a.length;
        int d = n / 2;
        while (d > 0) {
            for (int i = d; i < n; i++) {
                int j = i - d;
                while (j >= 0 && a[j] > a[j + d]) {
                    int tmp = a[j];
                    a[j] = a[j + d];
                    a[j + d] = tmp;
                    j = j - d;
                }
            }
            d = d / 2;
        }
    }

    public static void main(String[] args) {
        int[] b = {49, 38, 65, 97, 76, 13, 27, 50};
        shellSort(b);
        for (int i : b) {
            System.out.println(i + "");
        }
    }
}
