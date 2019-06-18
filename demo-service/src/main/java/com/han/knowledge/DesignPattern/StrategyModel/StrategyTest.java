package com.han.knowledge.DesignPattern.StrategyModel;

/**
 * Created by hp on 2017-05-30.
 * 客户端代码
 */
public class StrategyTest {
    public static void main(String[] args) {

        //由于实例化不同的策略，所以最终在调用context.ContextInterface();时，所获得的结果就不尽相同。
        Context context;
        context = new Context(new ConcreteStrategyA());
        context.ContextInterface();

        context = new Context(new ConcreteStrategyB());
        context.ContextInterface();

        context = new Context(new ConcreteStrategyC());
        context.ContextInterface();
    }
}
