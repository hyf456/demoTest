package com.han.knowledge.DesignPattern.StrategyModel;

/**
 * Created by hp on 2017-05-29.
 */
public class CashTest {
    static double total = 0.0d;

    //简单工厂模式
//    public static void main(String[] args) {
//        CashSuper cashSuper = CashFactory.createCashAccept(2);
//        double totalPrices = 0d;
//        totalPrices = cashSuper.acceptCash(43.4 * 3);
//        total = total + totalPrices;
//        System.out.println(total);
//    }

    //策略模式
    private void btnOk_Click(int type) {
        CashContext cashContext = null;

        //根据下拉选择框，将相应的策略对象作为参数传入CashContext的对象中
        switch (type) {
            case 1://正常收费
                cashContext = new CashContext(new CashNormal());
                break;
            case 2://满减
                cashContext = new CashContext(new CashReturn("300", "100"));
                break;
            case 3://打8折
                cashContext = new CashContext(new CashRebate("0.8"));
                break;
        }
        double totalPrices = 0d;
        //通过对Context的GetResult方法的调用，可以得到收取费用的结果，让具体算法与客户进行隔离。
        totalPrices = cashContext.GetResult(23);
        total = total + totalPrices;
        System.out.println(totalPrices);
        System.out.println(total);
    }

    //策略模式与工厂模式结合
    private void btnOk_Click2(int type) {
        CashContext cashContext = new CashContext(2);
        double totalPrices = 0d;
        totalPrices = cashContext.GetResult(43.4 * 3);
        total = total + totalPrices;
        System.out.println(total);
    }

    public static void main(String[] args) {
        CashTest cashTest = new CashTest();
        cashTest.btnOk_Click(3);
    }
}
