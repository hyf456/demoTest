package com.han.servlet.algorithm;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @Author: hanyf
 * @Description: Java实现括号是否匹配(给定一串字符串看括号是否成对出现)
 * @Date: 2019/6/6 15:44
 */
public class Match {

    public static boolean isMatch(String s) {
        // 定义左右括号的对应关系
        Map<Character, Character> bracket = new HashMap<>();
        bracket.put(')', '(');
        bracket.put(']', '[');
        bracket.put('}', '{');

        Stack stack = new Stack();

        for (int i = 0; i < s.length(); i++) {

            // 先转换成字符
            Character temp = s.charAt(i);
            // 是否为左括号
            if (bracket.containsValue(temp)) {
                stack.push(temp);
                // 是否为右括号
            } else if (bracket.containsKey(temp)) {
                if (stack.isEmpty()) {
                    return false;
                }
                // 若左右括号匹配
                if (stack.peek() == bracket.get(temp)) {
                    stack.pop();
                } else {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        //true
        System.out.println(isMatch("(***)-[{-------}]"));
        //true
        System.out.println(isMatch("(2+4)*a[5]"));
        //false
        System.out.println(isMatch("({}[]]])"));
        //false
        System.out.println(isMatch("())))"));

    }
}
