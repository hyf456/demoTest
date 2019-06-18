package com.han.knowledge.DesignPattern.StrategyModel;

/**
 * Created by hp on 2017-05-30.
 */
public class CashContext {
    //声明一个CashSuper对象
    private CashSuper cashSuper;

    //通过构造方法，传入具体的收费策略
    public CashContext(CashSuper csuper) {
        this.cashSuper = csuper;
    }

    public CashContext (int type) {
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
    }

    //根据收费策略的不同，获取计算结果
    public double GetResult(double money) {
        return cashSuper.acceptCash(money);
    }
}
