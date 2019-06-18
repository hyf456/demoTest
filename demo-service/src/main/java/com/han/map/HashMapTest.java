package com.han.map;

/**
 * @Author: hanyf
 * @Description:
 * @Date: 2019/1/4 10:50
 */
public class HashMapTest {

    /**
     * 功能描述 实现原理是什么？源码的实现方式是什么？
     * HashMap是干什么的？----》用来存储key，value的，存储数据的
     * 而计算机存储数据的时候都会有自己的特定存储方式，比如数据结构
     * 数组，链表，树形，图形
     * ArrayList 集合  也是用来存储数据的，我会发现ArrayList底层的数据结构就是数组
     *
     * LinkedList 集合 用来存储数据  底层数据结构是链表的方式 双向链表
     *
     * HashMap的数据结构
     * 数组+链表存储方式
     *
     * DEFAULT_INITIAL_CAPACITY 数组默认大小1<<4
     * MAXIMUM_CAPACITY 1<<30
     * DEFAULT_LOAD_FACTOR负载因子    0.75f
     * 容量是16当数组超过12的时候就会进行扩容    16*0.75 = 12
     * TREEIFY_THRESHOLD = 8 当数量超过8的时候使用红黑二叉树
     * UNTREEIFY_THRESHOLD = 6  当数量小于6的时候就需要变成链表
     *
     *
     *
     * @author hanyf
     * @date 2019/1/4
     * @param args
     * return void
     */
    public static void main(String[] args) {

    }
}
