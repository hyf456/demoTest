package com.han.responsibility.simple;

/**
 * @Author: hanyf
 * @Description:
 * @Date: 2018/11/5 10:51
 */
public class MainTest {
    public static void main(String[] args) {

        //需要被过滤的语句
        String msg = "被就业了：)，敏感信息，<script>";
        //实例化处理类
        MsgProcessor msgProcessor = new MsgProcessor(msg);
        String process = msgProcessor.process();
        System.out.println(process);
    }
}
