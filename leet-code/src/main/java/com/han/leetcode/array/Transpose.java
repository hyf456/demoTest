package com.han.leetcode.array;

/**
 * 给定一个矩阵 A， 返回 A 的转置矩阵。
 * 矩阵的转置是指将矩阵的主对角线翻转，交换矩阵的行索引与列索引。
 * @Description 转置矩阵
 * @Date 2020/9/24 18:34
 * @Author hanyf
 */
public class Transpose {

	public static void main(String[] args) {
		int[][] a = {{1, 2, 3}, {4, 5, 6}};
		// System.out.println(a.length + "" + a[0].length);
		int[][] transpose = transpose(a);
		for (int[] ints : transpose) {
			for (int anInt : ints) {
				System.out.println(anInt);
			}
		}
	}

	/**
	 * 方法：直接复制
	 * 思路和算法
	 *
	 * 尺寸为 R x C 的矩阵 A 转置后会得到尺寸为 C x R 的矩阵 ans，对此有 ans[c][r] = A[r][c]。
	 *
	 * 让我们初始化一个新的矩阵 ans 来表示答案。然后，我们将酌情复制矩阵的每个条目。
	 * 复杂度分析
	 *
	 * 时间复杂度：O(R * C)O(R∗C)，其中 RR 和 CC 是给定矩阵 A 的行数和列数。
	 *
	 * 空间复杂度：O(R * C)O(R∗C)，也就是答案所使用的空间。
	 * @param a
	 * @return
	 */
	public static int[][] transpose(int[][] a) {
		int r = a.length, c = a[0].length;
		int[][] ans = new int[c][r];
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				System.out.println(j + "-" + i);
				ans[j][i] = a[i][j];
			}
		}
		return ans;
	}
}
