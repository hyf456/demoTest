package com.han.knowledge.DesignPattern.SimpleFactory;

/**
 * Created by hp on 2017-05-29.
 */
public class OperationTest {
    public static double GetResult(double numberA, double numberB, int operate) {
        double result = 0d;
//        switch (operate) {
//            case "+":
//                result = numberA + numberB;
//                break;
//            case "-":
//                result = numberA = numberB;
//                break;
//            case "*":
//                result = numberA * numberB;
//                break;
//            case "/":
//                result = numberA / numberB;
//                break;
//        }
        switch (operate) {
            case 1:
                result = numberA + numberB;
                break;
            case 2:
                result = numberA = numberB;
                break;
            case 3:
                result = numberA * numberB;
                break;
            case 4:
                result = numberA / numberB;
                break;
        }
        return result;
    }

//    public static void main(String[] args) {
//        try {
//            System.out.println("请输入数字A:");
//            Scanner scanner = newcache Scanner(System.in);
//            String next = scanner.next();
//            System.out.println("请输入运算符号:");
//            Scanner scanner2 = newcache Scanner(System.in);
//            String next2 = scanner.next();
//            int operate = 0;
//            if (next2.equals("+")) {
//                operate = 1;
//            } else if (next2.equals("-")) {
//                operate = 2;
//            } else if (next2.equals("*")) {
//                operate = 3;
//            } else if(next2.equals("/")) {
//                operate = 4;
//            }
//            System.out.println("请输入数字B:");
//            Scanner scanner3 = newcache Scanner(System.in);
//            String next3 = scanner.next();
//            Double strResult = 0D;
//            strResult = OperationTest.GetResult
//                    (Double.parseDouble(next),Double.parseDouble(next3), operate);
//            System.out.println(strResult);
//        } catch (Exception e) {
//            System.out.println("异常");
//        }
//    }

    public static void main(String[] args) {
        Operation operation ;
        operation = OpeartionFactory.CreateOperate(1);
        operation.set_numberA(21);
        operation.set_numberB(34);
        double v = operation.GetResult();
        System.out.println(v);
    }
}
