package com.han.aspectJ;

/**
 * @Author:
 * @Description: aop登录模块
 * @Date: 2018/4/16 15:00
 */
public class AOPLoginUtils {
    private static final String TAG = "OOP";

    @TimeTrace(value = "登录")
    public static boolean Login(String userName, String passWord){

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if ("张三".equals(userName) && "123456".equals(passWord)){
            return true;
        }else{
            return false;
        }
    }
}
