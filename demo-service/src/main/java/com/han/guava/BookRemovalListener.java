package com.han.guava;

import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;


/**
 * @Author:
 * @Description:
 * @Date: 2018/5/16 15:56
 */
public class BookRemovalListener implements RemovalListener<String, Book> {
    @Override
    public void onRemoval(RemovalNotification<String, Book> removalNotification) {

    }
}
