package com.han.knowledge.FactoryPattern;

import com.han.knowledge.SimpleFactory.ParkTypeEnum;

/**
 * Created by hp on 2017-04-10.
 */
public class ParkB implements IPark {
    public void GetParkInfo() {
        ParkTypeEnum.ParkA.name();
        System.out.println("ParkB");
    }
}
