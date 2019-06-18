package com.han.knowledge.FactoryPattern;

/**
 * Created by hp on 2017-04-10.
 */
public class ParkFactoryB implements IFactoryPark {

    public IPark CreatePark()
    {
        return new ParkB();
    }
}
