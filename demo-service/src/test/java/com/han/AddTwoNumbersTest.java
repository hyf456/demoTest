package com.han;

import lombok.Data;

/**
 * @Author: hanyf
 * @Description: 两数相加
 * @Date: 2019/3/11 17:58
 */
public class AddTwoNumbersTest {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummyHead = null;
        ListNode curr = null;
        ListNode p = l1, q = l2;
        int carry = 0;
        while (p != null || q != null) {
            int x = p != null ? p.val : 0;
            int y = q != null ? q.val : 0;
            int sum = carry + x + y;
            carry = sum / 10;
            if (dummyHead == null) {
                dummyHead = new ListNode(sum % 10);
                curr = dummyHead;
            } else {
                curr.next = new ListNode(sum % 10);
                curr = curr.next;
            }
            if (p != null) {
                p = p.next;
            }
            if (q != null) {
                q = q.next;
            }
        }
        if (carry > 0) {
            curr.next = new ListNode(carry);
        }
        return dummyHead;
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(2);
        ListNode centre = l1;
        centre.next = new ListNode(4);
        centre = centre.next;
        centre.next = new ListNode(3);
        System.out.println(l1);

        ListNode l2 = new ListNode(5);
        ListNode centre2 = l2;
        centre2.next = new ListNode(6);
        centre2 = centre2.next;
        centre2.next = new ListNode(4);

        AddTwoNumbersTest a = new AddTwoNumbersTest();
        ListNode listNode = a.addTwoNumbers(l1, l2);
        System.out.println(listNode);
    }
}

@Data
class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }
}
