package com.han.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: hanyf
 * @Description: 将一个数组中的数以二叉树的存储结构存储，并遍历打印
 * https://blog.csdn.net/skylibiao/article/details/81195219
 * @Date: 2019/5/28 16:50
 */
public class Bintree {
    public Bintree left;
    public Bintree right;
    public Bintree root;
    // 数据域
    private Object data;
    // 存节点
    public List<Bintree> datas;

    public Bintree(Bintree left, Bintree right, Object data) {
        this.left = left;
        this.right = right;
        this.data = data;
    }

    // 将初始的左右孩子值为空
    public Bintree(Object data) {
        this(null, null, data);
    }

    public Bintree() {

    }

    public void creat(Object[] objs) {
        datas = new ArrayList<Bintree>();
        // 将一个数组的值依次转换为Node节点
        for (Object o : objs) {
            datas.add(new Bintree(o));
        }
        // 第一个数为根节点
        root = datas.get(0);
        // 建立二叉树
        for (int i = 0; i < objs.length / 2; i++) {
            // 左孩子
            datas.get(i).left = datas.get(i * 2 + 1);
            // 右孩子
            //避免偶数的时候 下标越界
            if (i * 2 + 2 < datas.size()) {
                datas.get(i).right = datas.get(i * 2 + 2);
            }
        }
    }

    //先序遍历
    public void preorder(Bintree root) {
        if (root != null) {
            System.out.println(root.data);
            preorder(root.left);
            preorder(root.right);
        }
    }

    //中序遍历
    public void inorder(Bintree root) {
        if (root != null) {
            inorder(root.left);
            System.out.println(root.data);
            inorder(root.right);
        }
    }

    //    后序遍历
    public void afterorder(Bintree root) {
        if (root != null) {
            System.out.println(root.data);
            afterorder(root.left);
            afterorder(root.right);
        }
    }

    public static void main(String[] args) {
        Bintree bintree = new Bintree();
        Object[] a = {2, 4, 5, 7, 1, 6, 12, 32, 51, 22};
        bintree.creat(a);
        bintree.preorder(bintree.root);
    }

}
