package com.han.leetcode.array;

/**
 * @Description 转置矩阵
 * @Date 2020/9/24 18:34
 * @Author hanyf
 */
public class Transpose {

	public static void main(String[] args) {
		int[][] a = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
		int[][] transpose = transpose(a);
	}

	public static int[][] transpose(int[][] a) {
		int r = a.length, c = a[0].length;
		int[][] ans = new int[c][r];
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				ans[c][r] = a[i][j];
			}
		}
		return ans;
	}
}
