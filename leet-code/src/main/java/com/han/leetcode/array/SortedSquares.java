package com.han.leetcode.array;

/**
 * @Description 给定一个按非递减顺序排序的整数数组 A，返回每个数字的平方组成的新数组，要求也按非递减顺序排序。
 * @Date 2020/11/25 10:02
 * @Author hanyf
 */
public class SortedSquares {

	/**
	 * 我们可以使用两个指针分别指向位置 00 和 n-1n−1，每次比较两个指针对应的数，选择较大的那个逆序放入答案并移动指针。这种方法无需处理某一指针移动至边界的情况，读者可以仔细思考其精髓所在。
	 * 复杂度分析
	 * 时间复杂度：O(n)O(n)，其中 nn 是数组 AA 的长度。
	 * 空间复杂度：O(1)O(1)。
	 * @return
	 */
	public int[] sortedSquares(int[] A) {
		int n = A.length;
		int[] ans = new int[n];
		for (int i = 0, j = n - 1, pos = n - 1; i <= j; ) {
			if (A[i] * A[i] > A[j] * A[j]) {
				ans[pos] = A[i] * A[i];
				++i;
			} else {
				ans[pos] = A[j] * A[j];
				--j;
			}
			--pos;
		}
		return ans;
	}
}
