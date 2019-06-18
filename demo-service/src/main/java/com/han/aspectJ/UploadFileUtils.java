package com.han.aspectJ;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @Author:
 * @Description: 文件上传模块
 * @Date: 2018/4/16 14:39
 */
public class UploadFileUtils {

    private static final String TAG = "OOP";
    public static Log log = LogFactory.getLog(UploadFileUtils.class);

    public static boolean upload(String url, String path) {
        long start = System.currentTimeMillis();
        long end;
        StringBuffer stringBuffer1 = new StringBuffer();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("从本地上传")
                .append(path)
                .append("到")
                .append(url);
        log.error(TAG + stringBuffer.toString());

        end = System.currentTimeMillis();
        stringBuffer1.append("文件上传成功，耗时：")
                .append(end - start);
        log.error(TAG + stringBuffer1.toString());

        return true;
    }
}
