package com.han.aspectJ;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @Author:
 * @Description: 登录模块
 * @Date: 2018/4/16 14:33
 */
public class LoginUtils {
    private static final String TAG = "OOP";
    public static Log log = LogFactory.getLog(LoginUtils.class);

    public static boolean Login(String userName, String passWord) {
        long start = System.currentTimeMillis();
        long end;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if ("张三".equals(userName) && "123456".equals(passWord)) {
            end = System.currentTimeMillis();
            stringBuffer.append("登录成功，耗时：")
                    .append(end - start);
            log.error(TAG + stringBuffer.toString());
            return true;
        } else {
            end = System.currentTimeMillis();
            stringBuffer.append("登录失败，耗时：")
                    .append(end - start);
            log.error(TAG + stringBuffer.toString());
            return false;
        }
    }
}
