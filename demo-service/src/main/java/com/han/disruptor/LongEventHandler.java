package com.han.disruptor;

import com.lmax.disruptor.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: hanyf
 * @Description:
 * @Date: 2018/11/12 19:42
 */
public class LongEventHandler implements EventHandler<LongEvent> {
    private final static Logger LOGGER = LoggerFactory.getLogger(LongEventHandler.class);
    @Override
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws InterruptedException {
        LOGGER.info("消费 Event=[{}]",event.getValue()) ;
        Thread.sleep(1000);
    }
}
