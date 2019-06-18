package com.han.knowledge.SimpleFactory;

/**
 * Created by hp on 2017-04-10.
 */
public class ParkFactory {
    public static IPark CreatePark(ParkTypeEnum parkType) {
        switch (parkType) {
            case ParkA : return new ParkA();
            case ParkB : return new ParkB();
            default : return null;
        }
    }
}
