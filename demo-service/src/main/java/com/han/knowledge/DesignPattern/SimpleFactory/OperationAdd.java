package com.han.knowledge.DesignPattern.SimpleFactory;

/**
 * Created by hp on 2017-05-29.
 */
public class OperationAdd extends Operation {

    public double GetResult() {
        double result = 0;
        result = _numberA + _numberB;
        return result;
    }
}
