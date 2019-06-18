package com.han.servlet.algorithm;

import java.util.LinkedList;

/**
 * @Author: hanyf
 * @Description: 二叉树遍历
 * @Date: 2019/6/3 16:54
 */
public class BinaryNode {

    private Object date;
    private BinaryNode left;
    private BinaryNode right;

    public BinaryNode() {
    }

    public BinaryNode(Object date, BinaryNode left, BinaryNode right) {
        this.date = date;
        this.left = left;
        this.right = right;
    }

    public Object getDate() {
        return date;
    }

    public void setDate(Object date) {
        this.date = date;
    }

    public BinaryNode getLeft() {
        return left;
    }

    public void setLeft(BinaryNode left) {
        this.left = left;
    }

    public BinaryNode getRight() {
        return right;
    }

    public void setRight(BinaryNode right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "BinaryNode{" +
                "date=" + date +
                ", left=" + left +
                ", right=" + right +
                '}';
    }

    public BinaryNode createNode() {
        BinaryNode node = new BinaryNode("1", null, null);
        BinaryNode left2 = new BinaryNode("2", null, null);
        BinaryNode left3 = new BinaryNode("3", null, null);
        BinaryNode left4 = new BinaryNode("4", null, null);
        BinaryNode left5 = new BinaryNode("5", null, null);
        BinaryNode left6 = new BinaryNode("6", null, null);

        node.setLeft(left2);
        left2.setLeft(left4);
        left2.setRight(left6);
        node.setRight(left3);
        left3.setRight(left5);
        return node;
    }

    /**
     * 功能描述 二叉树的层序遍历 借助于队列来实现 借助队列的先进先出的特性
     * 首先讲根结点入队列 然后遍历队列。
     * 首先将根节点打印出来，接着判断左节点是否为空 不为空则加入队列
     */
    public void levelIterator(BinaryNode node) {
        LinkedList<BinaryNode> queue = new LinkedList<>();
        // 先将根节点入队
        queue.offer(node);
        BinaryNode current;
        while (!queue.isEmpty()) {
            // 检查并删除此列表的头部（第一个元素）
            current = queue.poll();
            System.out.println(current.date + "---");
            if (current.getLeft() != null) {
                queue.offer(current.getLeft());
            }
            if (current.getRight() != null) {
                queue.offer(current.getRight());
            }
        }
    }
    public static void main(String[] args) {
        BinaryNode binaryNode = new BinaryNode();
        BinaryNode node = binaryNode.createNode();
        binaryNode.levelIterator(node);
    }
}
