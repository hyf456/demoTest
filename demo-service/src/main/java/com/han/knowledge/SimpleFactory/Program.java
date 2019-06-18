package com.han.knowledge.SimpleFactory;

/**
 * Created by hp on 2017-04-10.
 */
public class Program {
    public static void main(String[] args)
    {
        IPark park = ParkFactory.CreatePark(ParkTypeEnum.ParkA);
        park.GetParkInfo();
    }
}
