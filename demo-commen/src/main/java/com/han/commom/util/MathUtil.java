package com.han.commom.util;

import java.math.BigDecimal;

/**
 * @Description 数字计算工具
 * @ClassName MathUtil
 * @Author hanyunfei1
 * @Date 2023/3/18 14:55
 * @Version 1.0
 **/
public class MathUtil {

    private BigDecimal result = BigDecimal.ZERO;

    public MathUtil(Object result) {
        this.result = NumberUtil.toBigDecimal(result, AMOUNT_SCALE);
    }

    public MathUtil() {
        this.result = BigDecimal.ZERO;
    }

    public BigDecimal result(){
        return this.result;
    }

    public static final int AMOUNT_SCALE = 2;

    public static BigDecimal add(Object a, Object b) {
        return NumberUtil.toBigDecimal(a, AMOUNT_SCALE).add(NumberUtil.toBigDecimal(b, AMOUNT_SCALE));
    }

    public static BigDecimal subtract(Object a, Object b) {
        return NumberUtil.toBigDecimal(a, AMOUNT_SCALE).subtract(NumberUtil.toBigDecimal(b, AMOUNT_SCALE));
    }

    public static BigDecimal multiply(Object a, Object b) {
        return NumberUtil.toBigDecimal(a, AMOUNT_SCALE).multiply(NumberUtil.toBigDecimal(b, AMOUNT_SCALE));
    }

    public static BigDecimal divide(Object a, Object b, int scale) {
        return NumberUtil.toBigDecimal(a, scale).divide(NumberUtil.toBigDecimal(b, scale), scale, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal divide(Object a, Object b) {
        return divide(a, b, AMOUNT_SCALE);
    }

    public static int compareTo(BigDecimal a, BigDecimal b){
        return NumberUtil.toBigDecimal(a, AMOUNT_SCALE).compareTo(NumberUtil.toBigDecimal(b,AMOUNT_SCALE));
    }

    public MathUtil add(Object b) {
        this.result = this.result.add(NumberUtil.toBigDecimal(b, AMOUNT_SCALE));
        return this;
    }

    public MathUtil subtract(Object b) {
        this.result = this.result.subtract(NumberUtil.toBigDecimal(b, AMOUNT_SCALE));
        return this;
    }

    public MathUtil multiply(Object b) {
        this.result = this.result.multiply(NumberUtil.toBigDecimal(b, AMOUNT_SCALE));
        return this;
    }

    public MathUtil divide(Object divisor) {
        this.result = this.result.divide(NumberUtil.toBigDecimal(divisor, AMOUNT_SCALE), AMOUNT_SCALE, BigDecimal.ROUND_HALF_UP);
        return this;
    }

    public MathUtil divide(Object divisor, int scale) {
        this.result = this.result.divide(NumberUtil.toBigDecimal(divisor, AMOUNT_SCALE), scale, BigDecimal.ROUND_HALF_UP);
        return this;
    }

    public MathUtil clear(){
        this.result = BigDecimal.ZERO;
        return this;
    }
}
