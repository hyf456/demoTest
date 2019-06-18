package com.han.knowledge.DesignPattern.StrategyModel;

/**
 * Created by hp on 2017-05-29.
 * 正常收费子类
 */
public class CashNormal extends CashSuper {

    /**
     * 正常收费原价返回
     * @param money
     * @return
     */
    @Override
    public double acceptCash(double money) {
        return money;
    }
}
