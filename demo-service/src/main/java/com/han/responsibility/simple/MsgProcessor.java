package com.han.responsibility.simple;

import lombok.Data;
import lombok.ToString;

/**
 * @Author: hanyf
 * @Description:
 * @Date: 2018/11/5 10:53
 */
@Data
@ToString
public class MsgProcessor {
    private String msg;

    public MsgProcessor(String msg) {
        this.msg = msg;
    }

    public String process() {
        String r = msg;
        //过滤msg中的HTML标记
        r = r.replaceAll("<.*>", "");

        //guo过滤敏感词
        r = r.replaceAll("敏感信息", "").replaceAll("被就业", "就业");
        return r;
    }
}

