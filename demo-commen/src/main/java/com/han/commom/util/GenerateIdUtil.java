package com.yestae.base.user.service.util;

import com.yestae.framework.id.client.SnowflakeIdWorker;

import java.util.List;

/**
 * @Description 生成 Id Util
 * @Date 2019/8/26 11:41
 * @Author hanyf
 */
public class GenerateIdUtil {

    public static long nextId() {
        // 生成单个ID
        long id = SnowflakeIdWorker.nextId();
        return id;
    }

    public static List<Long> nextId(int count) {
        // 批量生成1000个ID
        List<Long> idList = SnowflakeIdWorker.nextId(1000);
        return idList;
    }
}
