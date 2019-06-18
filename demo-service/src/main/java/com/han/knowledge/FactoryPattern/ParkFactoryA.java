package com.han.knowledge.FactoryPattern;

/**
 * Created by hp on 2017-04-10.
 */
public class ParkFactoryA implements IFactoryPark {
    public IPark CreatePark()
    {
        return new ParkA();
    }
}
