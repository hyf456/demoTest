package com.han.guava.callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @Author: hanyf
 * @Description:
 * @Date: 2018/11/9 19:49
 */
public class Notifier {

    private final static Logger LOGGER = LoggerFactory.getLogger(Notifier.class);

    public void execute(Caller caller, String msg) throws InterruptedException {
        LOGGER.info("收到消息=【{}】", msg);

        LOGGER.info("等待响应中。。。。。");
        TimeUnit.SECONDS.sleep(2);


        caller.getCallBackListener().callBackNotify("我在北京！");

    }
}
