package com.han.disruptor;

import lombok.Data;
import lombok.ToString;

/**
 * @Author: hanyf
 * @Description:
 * @Date: 2018/11/12 10:54
 */
@Data
@ToString
public class LongEvent {

    private long value;

    public void set(long value) {
        this.value = value;
    }
}
