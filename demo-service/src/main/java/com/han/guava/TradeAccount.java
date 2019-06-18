package com.han.guava;

import lombok.Data;

/**
 * @Author:
 * @Description:
 * @Date: 2018/5/16 15:42
 */
@Data
public class TradeAccount {
    private String id; //ID
    private String owner; //所有者
    private double balance; //余额
}
