package com.han.guava;

import org.springframework.stereotype.Service;

/**
 * @Author:
 * @Description:
 * @Date: 2018/5/16 15:51
 */
@Service
public class TradeAccountServiceImpl {

    public TradeAccount getTradeAccountById(String key) {
        TradeAccount tradeAccount = new TradeAccount();
        System.out.println("getTradeAccountById开始");
        if (key.equals("a")) {
            tradeAccount.setId("a");
            return tradeAccount;
        } else if (key.equals("b")) {
            tradeAccount.setId("b");
            return tradeAccount;
        } else if (key.equals("c")) {
            tradeAccount.setId("c");
            return tradeAccount;
        }
        tradeAccount.setId("ddd");
        return tradeAccount;
    }
}
