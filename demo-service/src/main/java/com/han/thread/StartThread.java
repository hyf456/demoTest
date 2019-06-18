package com.han.thread;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;

/**
 * @Author: hanyf
 * @Description:
 * @Date: 2018/11/8 15:50
 */
public class StartThread {

    @Resource(name = "consumerQueueThreadPool")
    private ExecutorService consumerQueueThreadPool;

    public void execute() {
        //消费队列
        for (int i = 0; i < 5; i++) {
            consumerQueueThreadPool.execute(new ConsumerQueueThread());
        }
    }
}
