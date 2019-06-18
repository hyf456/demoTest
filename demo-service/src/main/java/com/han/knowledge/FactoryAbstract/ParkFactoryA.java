package com.han.knowledge.FactoryAbstract;

/**
 * Created by hp on 2017-04-10.
 */
public class ParkFactoryA implements IFactory {
    public IPark CreatePark()
    {
        return new ParkA();
    }

    public ICar CreateCar()
    {
        return new CarA();
    }
}
