package com.han.knowledge.PhoneCell;


import java.util.HashMap;
import java.util.Map;


/**
 * 调用命中规则详情查询 GET接口服务的Java示例
 *
 * @author hanyf
 */
public class CallService {

    private final static String COMPANY_ID = "1"; // 合作方标识
    private final static String AUTH_CODE = "04e4c2b20fda11e7b48700163e134b7e"; // 合作方密钥


    public static void main(String[] args) throws Exception {
//        String ss = RequestUtil.invoke(sendDial());
//        String ss = RequestUtil.invoke(hangUp());
//        String ss = RequestUtil.invoke(getCDRList());
        String ss = RequestUtil.invoke(getRecord());
        System.out.println(ss);
    }

    //此接口用于页面点击拨号
    public static Map sendDial() {
        Map parameterMap = new HashMap();
        parameterMap.put("action ", "SendDial");
        parameterMap.put("companyid ", COMPANY_ID);                     //合作方标志
        parameterMap.put("auth", AUTH_CODE);                       //合作方密钥
        //以下为业务数据
        parameterMap.put("staffid", "8008");                //事件id
        parameterMap.put("phonenum", "18511870525");
        return parameterMap;
    }

    //此接口用于页面点击挂断
    public static Map hangUp() {
        Map parameterMap = new HashMap();
        parameterMap.put("action ", "HangUp");
        parameterMap.put("companyid ", COMPANY_ID);                     //合作方标志
        parameterMap.put("auth", AUTH_CODE);                       //合作方密钥
        //以下为业务数据
        parameterMap.put("staffid", "8008");

        return parameterMap;
    }

//    public boolean extendStatus() {
//
//        Map parameterMap = newcache HashMap();
//        parameterMap.put("action ", "ExtenStatus");
//        parameterMap.put("companyid ", COMPANY_ID);                     //合作方标志
//        parameterMap.put("auth", AUTH_CODE);                       //合作方密钥
//        //以下为业务数据
//        parameterMap.put("staffid", "14582xxxxx882566");
//
//        return true;
//    }

    public static Map getCDRList() {
        Map parameterMap = new HashMap();
        parameterMap.put("action ", "GetCDRList");
        parameterMap.put("companyid ", COMPANY_ID);                     //合作方标志
        parameterMap.put("auth", AUTH_CODE);                       //合作方密钥
        parameterMap.put("staffid", "8008");
        parameterMap.put("phonenum", "18511870525");                       //合作方密钥
        //以下为业务数据
        return parameterMap;

    }

    public static Map getRecord() {
        Map parameterMap = new HashMap();
        parameterMap.put("action ", "GetRecord");
        parameterMap.put("companyid ", COMPANY_ID);                     //合作方标志
        parameterMap.put("auth", AUTH_CODE);                       //合作方密钥
        //以下为业务数据
        parameterMap.put("uniqueid", "1490352731.14");

        return parameterMap;

    }

}
