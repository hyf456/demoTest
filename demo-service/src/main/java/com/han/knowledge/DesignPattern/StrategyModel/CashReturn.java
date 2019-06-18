package com.han.knowledge.DesignPattern.StrategyModel;

/**
 * Created by hp on 2017-05-29.
 * 返利收费子类
 */
public class CashReturn extends CashSuper {

    private double moneyCondition = 0.0d;
    private double moneyReturn = 0.0d;

    /**
     * 返利收费，初始化是必须要输入返利条件和返利值，
     * @param moneyCondition
     * @param moneyReturn
     */
    public CashReturn(String moneyCondition, String moneyReturn) {
        this.moneyCondition = Double.parseDouble(moneyCondition);
        this.moneyReturn = Double.parseDouble(moneyReturn);
    }
    @Override
    public double acceptCash(double money) {
        double result = money;
        //若大于返利条件，则需要减去返利值
        if (money >= moneyCondition) {
            result = money - (money / moneyCondition) * moneyReturn;
        }
        return result;
    }
}
