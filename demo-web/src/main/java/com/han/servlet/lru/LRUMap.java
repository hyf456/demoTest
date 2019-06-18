package com.han.servlet.lru;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: hanyf
 * @Description: LRU没有超时时间设置
 * @Date: 2019/5/31 16:05
 */
public class LRUMap<K, V> {

    private final Map<K, V> cacheMap = new HashMap<>();

    /**
     * 最大缓存大小
     */
    private int cacheSize;

    /**
     * 节结大小
     */
    private int nodeCount;

    /**
     * 头结点
     */
    private Node<K, V> header;

    /**
     * 尾结点
     */
    private Node<K, V> tailer;

    public LRUMap(int cacheSize) {
        this.cacheSize = cacheSize;
        // 头结点的下一个结点为空
        header = new Node<>();
        header.next = null;
        // 尾结点的上一个节点为空
        tailer = new Node<>();
        // 双向链表，头结点的上节点指向尾结点
        header.tail = tailer;
        //尾结点的下结点指向头结点
        tailer.next = header;
    }

    public void put(K key, V value) {
        cacheMap.put(key, value);
        // 双向链表中添加结点
        addNode(key, value);
    }

    public V get(K key) {
        Node<K, V> node = getNode(key);
        //移动到头结点
        moveToHead(node);
        return cacheMap.get(key);
    }

    private void moveToHead(Node<K, V> node) {
        // 如果是最后一个结点
        if (node.tail == null) {
            node.next.tail = null;
            tailer = node.next;
            nodeCount--;
        }

        // 如果是本来就是头结点不作处理
        if (node.next == null) {
            return ;
        }

        // 如果处于中间结点
        if (node.tail != null && node.next != null) {
            // 它的上一结点指向它的下一节点，也就删除当前结点
            node.tail.next = node.next;
            nodeCount--;
        }

        // 最后在头部增加当前结点
        // 注意这里需要重新 new 一个对象，不然原本的node 还有这下面的引用，会造成内存溢出。
        node = new Node<K, V>(node.getKey(), node.getValue());
        addHead(node);
    }

    /**
     * 链表查询 效率低
     *
     * @param key
     * @return
     */
    private Node<K, V> getNode(K key) {
        Node<K, V> node = tailer;
        while (node != null) {
            if (node.getKey().equals(key)) {
                return node;
            }
            node = node.next;
        }
        return null;
    }

    /**
     * 写入头结点
     * @param key
     * @param value
     */
    private void addNode(K key, V value) {
        Node<K, V> node = new Node<K, V>(key, value);

        // 容量满了删除最后一个
        if (cacheSize == nodeCount) {
            // 删除尾结点
            delTail();
        }

        // 写入头结点
        addHead(node);
    }

    /**
     * 添加头结点
     * @param node
     */
    private void addHead(Node<K, V> node) {

        // 写入头结点
        header.next = node;
        node.tail = header;
        header = node;
        nodeCount++;

        // 如果写入的数据大于 2 个，就将初始化的头尾节点删除
        if (nodeCount == 2) {
            tailer.next.next.tail = null;
            tailer = tailer.next.next;
        }
    }

    private void delTail() {
        // 把尾结点从缓存中删除
        cacheMap.remove(tailer.getKey());
        //删除尾结点
        tailer.next.tail = null;
        tailer = tailer.next;
        nodeCount--;
    }

    private class Node<K, V> {
        private K key;
        private V value;
        Node<K, V> tail;
        Node<K, V> next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public Node() {

        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder() ;
        Node<K, V> node = tailer ;
        while (node != null){
            sb.append(node.getKey()).append(":")
                    .append(node.getValue())
                    .append("-->") ;
            node = node.next ;
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        LRUMap<String,Integer> lruMap = new LRUMap(3) ;
        lruMap.put("1",1) ;
        lruMap.put("2",2) ;
        lruMap.put("3",3) ;
        System.out.println(lruMap.toString());
        lruMap.put("4",4) ;
        System.out.println(lruMap.toString());
        lruMap.put("5",5) ;
        System.out.println(lruMap.toString());
    }
}


