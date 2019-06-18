package com.han.responsibility.Filter;

/**
 * @Author: hanyf
 * @Description:
 * @Date: 2018/11/5 11:09
 */
public class MainTest {
    public static void main(String[] args) {

        //需要被过滤的语句
        String msg = "被就业了：)，敏感信息，<script>";
        //实例化处理类
        //MsgProcessor mp = new MsgProcessor(msg);
        MsgFilterProcessor mp = new MsgFilterProcessor(msg);
        String r = mp.process();
        System.out.println(r);
    }
}
