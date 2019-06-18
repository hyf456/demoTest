package com.han.knowledge.DesignPattern.StrategyModel;

/**
 * Created by hp on 2017-05-29.
 * 现金收费工厂
 */
public class CashFactory {
    public static CashSuper createCashAccept(int type) {
        CashSuper cashSuper = null;
        switch (type) {
            case 1://正常收费
                cashSuper = new CashNormal();
                break;
            case 2://满减
                cashSuper = new CashReturn("300", "100");
                break;
            case 3://打8折
                cashSuper = new CashRebate("0.8");
                break;
        }
        return cashSuper;
    }
}
