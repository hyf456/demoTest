package com.han.knowledge.SimpleFactory;

/**
 * Created by hp on 2017-04-10.
 */
public class ParkB implements IPark {
    public void GetParkInfo() {
        ParkTypeEnum.ParkA.name();
        System.out.println("ParkB");
    }
}
