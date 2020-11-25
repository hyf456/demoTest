package com.han.leetcode.array;

/**
 * @Description 数组中占比超过一半的元素称之为主要元素。给定一个整数数组，找到它的主要元素。若没有，返回-1。
 * @Date 2020/11/23 19:05
 * @Author hanyf
 */
public class MajorityElement {

	// 摩尔投票算法
	public int majorityElement(int [] a) {
		int major = 0, count = 0;
		for (int i : a) {
			if (count == 0) {
				major = i;
				count++;
			} else {
				if (major == i) {
					count++;
				} else {
					count--;
				}
			}
		}
		if (count > 0) {
			int sum = 0;
			for (int i : a) {
				if (major == i) {
					sum++;
				}
			}
			if (sum > (a.length / 2)) {
				return major;
			}
		}
		return -1;
	}
}
