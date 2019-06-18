package com.han.knowledge.FactoryClass;

/**
 * Created by hp on 2017-04-10.
 */
public class Program {
    public static void main(String[] args)
    {
        IPark park = ParkFactory.CreatePark("com.han.knowledge.FactoryClass.ParkA");
        park.GetParkInfo();
    }
}
