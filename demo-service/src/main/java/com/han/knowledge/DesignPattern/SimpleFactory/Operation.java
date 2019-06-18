package com.han.knowledge.DesignPattern.SimpleFactory;

/**
 * Created by hp on 2017-05-29.
 */
public abstract class Operation {
    public static double _numberA = 0;
    public static double _numberB = 0;

    public double get_numberA() {
        return _numberA;
    }

    public void set_numberA(double _numberA) {
        this._numberA = _numberA;
    }

    public double get_numberB() {
        return _numberB;
    }

    public void set_numberB(double _numberB) {
        this._numberB = _numberB;
    }

    public double GetResult() {
        double result = 0;
        return result;
    }
}
