package com.han.knowledge.DesignPattern.StrategyModel;

/**
 * Created by hp on 2017-05-29.
 * 现金收费抽象类
 */
public abstract class CashSuper {

    /**
     * 现金收取超类的抽象方法，收取现金，参数为原价，返回当前价
     * @param money
     * @return
     */
    public abstract double acceptCash(double money);
}
