package com.han.knowledge.DesignPattern.SimpleFactory;

/**
 * Created by hp on 2017-05-29.
 */
public class OpeartionFactory {

    public static Operation CreateOperate(int operate) {
        Operation operation = null;
        switch (operate) {
            case 1:
                operation = new OperationAdd();
                break;
            case 2:
                operation = new OperationSub();
                break;
            case 3:
                operation = new OperationMul();
                break;
            case 4:
                operation = new OpeartionDiv();
                break;
        }
        return operation;
    }
}
