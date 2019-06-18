package com.han.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * @Author: hanyf
 * @Description:
 * @Date: 2018/11/12 19:40
 */
public class LongEventFactory implements EventFactory<LongEvent> {

    @Override
    public LongEvent newInstance() {
        return new LongEvent();
    }
}
