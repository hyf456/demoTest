package com.han.knowledge.FactoryAbstract;

/**
 * Created by hp on 2017-04-10.
 */
public class Program {
    public static void main(String[] args)
    {
        IFactory factory = new ParkFactoryA();
        IPark park=factory.CreatePark();
        park.GetParkInfo();
        ICar car = factory.CreateCar();
        car.Run();
    }
}
