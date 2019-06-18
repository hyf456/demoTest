package com.han.knowledge.FactoryPattern;

/**
 * Created by hp on 2017-04-10.
 */
public class Program {

    public static void main(String[] args)
    {
        IFactoryPark factory = new ParkFactoryA();
        IPark park=factory.CreatePark();
        park.GetParkInfo();
    }
}
