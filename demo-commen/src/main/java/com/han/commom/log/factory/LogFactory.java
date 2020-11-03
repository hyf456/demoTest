// package com.yestae.base.user.service.log.factory;
//
// import com.yestae.base.user.model.entity.LoginLog;
// import com.yestae.base.user.model.entity.User;
// import com.yestae.base.user.service.util.GenerateIdUtil;
// import com.yestae.common.enums.BooleanEnum;
// import com.yestae.common.utils.DateUtil;
//
// /**
//  * @Description 日志存入数据库
//  * @Date 2019/8/1 11:25
//  * @Author hanyf
//  */
// public class LogFactory {
//
//
//     /**
//      * 创建登录日志
//      */
//     public static LoginLog createLoginLog(String browser, BooleanEnum booleanEnum, User user, String msg, String ip, String token) {
//         LoginLog loginLog = new LoginLog();
//         loginLog.setId(GenerateIdUtil.nextId());
//         long millis = DateUtil.getCurrentMillis();
//         loginLog.setCreateId(user.getId());
//         loginLog.setCreateName(user.getName());
//         loginLog.setCreateTime(millis);
//         loginLog.setUpdateId(user.getId());
//         loginLog.setUpdateName(user.getName());
//         loginLog.setUpdateTime(millis);
//         loginLog.setAccount(user.getAccount());
//         loginLog.setUserId(user.getId());
//         loginLog.setUserName(user.getName());
//         loginLog.setStatus(booleanEnum.getValue());
//         loginLog.setIp(ip);
//         loginLog.setBrowser(browser);
//         loginLog.setToken(token);
//         loginLog.setMessage(msg);
//         loginLog.setInternalMessage(msg);
//         return loginLog;
//     }
//
//     /**
//      * 创建登录日志
//      */
//     public static LoginLog createLoginLog(String browser, BooleanEnum booleanEnum, String userName, String msg, String internalMessage, String ip) {
//         LoginLog loginLog = new LoginLog();
//         loginLog.setId(GenerateIdUtil.nextId());
//         long millis = DateUtil.getCurrentMillis();
//         loginLog.setCreateTime(millis);
//         loginLog.setUpdateTime(millis);
//         loginLog.setAccount(userName);
//         loginLog.setUserName(userName);
//         loginLog.setStatus(booleanEnum.getValue());
//         loginLog.setIp(ip);
//         loginLog.setBrowser(browser);
//         loginLog.setMessage(msg);
//         loginLog.setInternalMessage(internalMessage);
//         return loginLog;
//     }
//
// }
