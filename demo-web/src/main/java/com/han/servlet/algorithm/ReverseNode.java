package com.han.servlet.algorithm;

import java.util.Stack;

/**
 * @Author: hanyf
 * @Description: 三种方式反向打印单向链表
 * @Date: 2019/6/3 18:09
 */
public class ReverseNode {

    /**
     * 利用栈的先进后出特性
     * @param node
     */
    public void reverseNode1(Node node) {
        System.out.println("=== 翻转之前 ===");

        Stack<Node> stack = new Stack<>();
        while (node != null) {
            System.out.println(node.value + "===>");

            stack.push(node);
            node = node.next;
        }

        System.out.println("");

        System.out.println("=== 翻转之后 ===");
        while (!stack.isEmpty()) {
            System.out.println(stack.pop().value + "===>");
        }
    }

    /**
     * 功能描述 利用头插法插入链表
     */
    public void reverseNode2(Node head) {
        if (head == null) {
            return;
        }

        // 最终翻转之后的 Node
        Node node;

        Node pre = null;
        Node cur = head;
        Node next;
        while (cur != null) {
            next = cur.next;

            // 链表的头插法
            cur.next = pre;
            pre = cur;

            cur = next;
        }
        node = pre;

        // 遍历新链表
        while (node != null) {
            System.out.println(node.value);
            node = node.next;
        }
    }

    /**
     * 功能描述 递归
     */
    public void recNode3(Node node) {
        if (node == null) {
            return;
        }

        if (node.next != null) {
            recNode3(node.next);
        }
        System.out.println(node.value + "===>");
    }

    public static class Node<T> {
        public T value;
        public Node<T> next;

        public Node(T value, Node<T> next) {
            this.next = next;
            this.value = value;
        }
    }

    public static void main(String[] args) {
        Node node5 = new Node(5, null);
        Node node4 = new Node(4, node5);
        Node node3 = new Node(3, node4);
        Node node2 = new Node(2, node3);
        Node node1 = new Node(1, node2);
        Node node = new Node(0, node1);
        ReverseNode reverseNode = new ReverseNode();
        reverseNode.reverseNode2(node);
    }
}
