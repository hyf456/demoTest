package com.han;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: hanyf
 * @Description:
 * @Date: 2019/2/26 11:10
 */
public class DemoTest {

    // public static void main(String[] args) {
    //     StringBuffer append = new StringBuffer().append('A').append("B");
    //     StringBuffer append1 = new StringBuffer('A').append('B');
    //     StringBuffer append2 = new StringBuffer("A").append("B");
    //
    //     System.out.println(append == append1);
    //     System.out.println(append == append2);
    //     System.out.println(append1 == append2);
    //
    //     String s = "who";
    //     System.out.println("who" == s);
    //     System.out.println("who" == "who");
    //     System.out.println("who" == new String("who"));
    //     System.out.println("who" == new String("who").intern());
    // }

    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        int len = nums.length;
        int original = 0;
        int two;
        int threed;
        int zero = 0;
        for (int i=0; i<len; i++) {
            if (original == nums[i] && original != 0) {
                continue;
            }
            original = nums[i];
            int a = i;
            int b = a + 1;
            int c = len - 1;
            while (b < c) {
                int twoSum = nums[a] + nums[b];
                int threeSum = twoSum + nums[c];
                if (threeSum > 0) {
                    c--;
                } else if (threeSum < 0) {
                    b++;
                } else if (threeSum == 0) {
                    two = nums[b];
                    if (original == 0 && two == 0) {
                        if (zero != 0) {
                            break;
                        }
                        zero++;
                    }
                    threed = nums[c];
                    List<Integer> oneResult = new ArrayList<>();
                    oneResult.add(nums[a]);
                    oneResult.add(nums[b]);
                    oneResult.add(nums[c]);
                    System.out.println(nums[a] + "  " + nums[b] + "   " + nums[c]);
                    result.add(oneResult);
                    do {
                        b++;
                        if (b >= c) {
                            break;
                        }
                    } while (two == nums[b]);

                    do {
                        c--;
                        if (b >= c) {
                            break;
                        }
                    } while (threed == nums[b]);


                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] nums = {0, 0, 0, 0};
        DemoTest s = new DemoTest();
        s.threeSum(nums);
    }
}
