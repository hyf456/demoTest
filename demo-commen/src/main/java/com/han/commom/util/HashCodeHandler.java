package com.han.commom.util;

public class HashCodeHandler {

    private static final int MIN_ENUM_VALUE = 1;
    private static final int MAX_ENUM_VALUE = 10000;

    public static int getHashCodeEnumValue(String outerNo) {
        int hashCode = outerNo.hashCode();
        // 确保hashCode的值在1到10000之间
        System.out.println(hashCode);
        return (hashCode % (MAX_ENUM_VALUE - MIN_ENUM_VALUE + 1)) + MIN_ENUM_VALUE;
    }

    public static void main(String[] args) {
        String outerNo = "203784298";
        int i = Math.abs(getHashCodeEnumValue(outerNo)) % 2048;
        System.out.println("table: " + i);
        int i1 = (i) / 128;
        System.out.println("db: " + i1);
    }
}
