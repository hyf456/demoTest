package com.han.disruptor;

import com.lmax.disruptor.RingBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

/**
 * @Author: hanyf
 * @Description:
 * @Date: 2018/11/12 19:45
 */
public class LongEventProducer {
    private final static Logger LOGGER = LoggerFactory.getLogger(LongEventProducer.class);
    private final RingBuffer<LongEvent> ringBuffer;

    public LongEventProducer(RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(ByteBuffer bb) {
        // Grab the next sequence
        long sequence = ringBuffer.next();
        try {
            // Get the entry in the Disruptor
            LongEvent event = ringBuffer.get(sequence);
            // for the sequence
            long aLong = bb.getLong(0);
            LOGGER.info("product=[{}]",aLong);
            // Fill with data
            event.set(aLong);
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}
