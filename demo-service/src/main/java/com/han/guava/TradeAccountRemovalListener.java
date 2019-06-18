package com.han.guava;

import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import lombok.Data;

/**
 * @Author:
 * @Description: 缓存中的条目被移除后接收通知
 * @Date: 2018/5/16 15:48
 */
@Data
public class TradeAccountRemovalListener implements RemovalListener<String, TradeAccount> {
    @Override
    public void onRemoval(RemovalNotification<String, TradeAccount> removalNotification) {
        System.out.println("移除缓存数据:" + removalNotification);
    }
}
