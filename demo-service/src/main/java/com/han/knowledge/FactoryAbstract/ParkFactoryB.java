package com.han.knowledge.FactoryAbstract;

/**
 * Created by hp on 2017-04-10.
 */
public class ParkFactoryB implements IFactory {
    public IPark CreatePark()
    {
        return new ParkB();
    }

    public ICar CreateCar()
    {
        return new CarB();
    }
}
