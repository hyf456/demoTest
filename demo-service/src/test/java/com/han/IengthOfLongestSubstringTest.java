package com.han;

import java.util.HashMap;

/**
 * @Author: hanyf
 * @Description: 无重复字符的最长子串
 * @Date: 2019/3/11 18:29
 */
public class IengthOfLongestSubstringTest {

    public int lengthOfLongestSubstring(String s) {
        int result = 0;
        int count = 0;
        int length = s.length();
        HashMap<Character, Integer> map = new HashMap();
        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);
            boolean b = map.containsKey(c);
            if (b) {
                count = 0;
            }
            map.put(c, count);
            count++;
            if (count > result) {
                result = count;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String s = "sddklfqdwe";
        IengthOfLongestSubstringTest i = new IengthOfLongestSubstringTest();
        int i1 = i.lengthOfLongestSubstring(s);
        System.out.println(i1);
    }
}
