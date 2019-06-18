package com.han.bloom.filters;

/**
 * @Author: hanyf
 * @Description: 布隆过滤
 * @Date: 2018/12/13 13:35
 */
public class BloomFilters {

    /**   
     * 数组长度
     */
    private int arraySize;

    /**   
     * 数组
     */
    private int[] array;

    public BloomFilters(int arraySize) {
        this.arraySize = arraySize;
        array = new int[arraySize];
    }

    /**
     * 功能描述 写入数据
     *
     * @param
     * @return
     * @author hanyf
     * @date 2018/12/13
     */
    public void add(String key) {
        int first = hashCode_1(key);
        int second = hashcode_2(key);
        int third = hashcode_3(key);

        array[first % arraySize] = 1;
        array[second % arraySize] = 1;
        array[third % arraySize] = 1;
    }

    /**   
     *功能描述 判断数据是否存在
     * @author hanyf
     * @date 2018/12/13
     * @param key
     * @return boolean
     */
    public boolean check(String key) {
        int first = hashCode_1(key);
        int second = hashcode_2(key);
        int third = hashcode_3(key);

        int firstIndex = array[first % arraySize];
        if (firstIndex == 0) {
            return false;
        }

        int secondIndex = array[second % arraySize];
        if (secondIndex == 0) {
            return false;
        }

        int thirdIndex = array[third % arraySize];
        if (thirdIndex == 0) {
            return false;
        }
        return true;
    }

    /**   
     *功能描述 算法1
     * @author hanyf
     * @date 2018/12/13
     * @param key 
     * @return int
     */
    private int hashCode_1(String key) {
        int hash = 0;
        int i;
        for (i = 0; i < key.length(); i++) {
            hash = 33 * hash + key.charAt(i);
        }
        return Math.abs(hash);
    }

    /**   
     *功能描述 hash 算法2
     * @author hanyf
     * @date 2018/12/13
     * @param data 
     * @return int
     */
    private int hashcode_2(String data) {
        final int p = 16777619;
        int hash = (int) 2166126261L;
        for (int i = 0; i < data.length(); i++) {
            hash = (hash ^ data.charAt(i)) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
        return Math.abs(hash);
    }

    /**   
     *功能描述 hash 算法3
     * @author hanyf
     * @date 2018/12/13
     * @param key 
     * @return int
     */
    private int hashcode_3(String key) {
        int hash, i;
        for (hash = 0, i = 0; i < key.length(); i++) {
            hash += key.charAt(i);
            hash += (hash << 10);
            hash ^= (hash >> 6);
        }
        hash += (hash << 3);
        hash ^= (hash >> 11);
        hash += (hash << 15);
        return Math.abs(hash);
    }
}
