package com.han.knowledge.DesignPattern.StrategyModel;

/**
 * Created by hp on 2017-05-30.
 * 用一个ConcreteStrategy来配置，维护一个对Strategy对象的引用
 */
public class Context {
    Strategy strategy;
    //初始化是传入具体的粗略对象
    public Context (Strategy strategy) {
        this.strategy = strategy;
    }
    //上下文接口
    //根据具体的策略对象，调用起算法的方法
    public void ContextInterface() {
        strategy.AlgorithmInterface();
    }
}
