package com.han.aspectJ;

/**
 * @Author:
 * @Description:
 * @Date: 2018/4/16 14:52
 */
public class Main {

    public static void main(String[] args) {
        LoginUtils.Login("张三", "123456");
        UploadFileUtils.upload("/erw", "/er");
    }
}
