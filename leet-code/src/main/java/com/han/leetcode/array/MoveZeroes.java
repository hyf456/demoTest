package com.han.leetcode.array;

/**
 * @Description 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
 * @Date 2020/11/19 17:32
 * @Author hanyf
 */
public class MoveZeroes {

	public void moveZeroes(int[] nums) {
		if(nums==null) {
			return;
		}
		//第一次遍历的时候，j指针记录非0的个数，只要是非0的统统都赋给nums[j]
		int j = 0;
		for(int i=0;i<nums.length;++i) {
			if(nums[i]!=0) {
				nums[j++] = nums[i];
			}
		}
		//非0元素统计完了，剩下的都是0了
		//所以第二次遍历把末尾的元素都赋为0即可
		for(int i=j;i<nums.length;++i) {
			nums[i] = 0;
		}
	}

	public void moveZeroes1(int[] nums) {
		if (nums == null || nums.length == 0)
			return;
		int index = 0;
		//一次遍历，把非零的都往前挪
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] != 0)
				nums[index++] = nums[i];
		}
		//后面的都是0,
		while (index < nums.length) {
			nums[index++] = 0;
		}
	}

	public void moveZeroes3(int[] nums) {
		int i = 0;
		for (int j = 0; j < nums.length; j++) {
			//只要不为0就往前挪
			if (nums[j] != 0) {
				//i指向的值和j指向的值交换
				int temp = nums[i];
				nums[i] = nums[j];
				nums[j] = temp;

				i++;
			}
		}
	}

}
