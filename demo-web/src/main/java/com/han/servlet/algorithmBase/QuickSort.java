package com.han.servlet.algorithmBase;

/**
 * @Author: hanyf
 * @Description: 快速排序  平均O(nlogn),最好O(nlogn),最坏O(n^2);空间复杂度O(nlogn);不稳定;较复杂
 * 算法描述：对于一组给定的记录，通过一趟排序后，将原序列分为两部分，其中前一部分的所有记录均比后一部分的所有记录小，
 * 然后再依次对前后两部分的记录进行快速排序，递归该过程，直到序列中的所有记录均有序为止。
 * @Date: 2019/6/13 10:22
 */
public class QuickSort {

    public static void sort(int[] a, int low, int high) {
        if(low>=high) {
            return;
        }
        int i = low;
        int j = high;
        int key = a[i];
        while (i < j) {
            while (i < j && a[j] >= key) {
                j--;
            }
            a[i++] = a[j];
            while (i < j && a[i] <= key) {
                i++;
            }
            a[j--] = a[i];
        }
        a[i] = key;
        sort(a,low,i-1);
        sort(a,i+1,high);
    }

    public static void quickSort(int[] a) {
        sort(a, 0, a.length-1);
        for(int i:a) {
            System.out.print(i + " ");
        }
    }

    public static void main(String[] args) {
        int[] a = { 49, 38, 65, 97, 76, 13, 27, 50 };
        quickSort(a);
    }
}
