package com.han.commom.util;

import org.slf4j.Logger;


/**
 * @Description JSON日志处理工具类，提供统一的JSON操作日志记录方法
 * @ClassName JsonLogUtils
 * @Author hanyunfei1
 * @Date 2025/8/27 09:44
 * @Version 1.0
 **/
public class LoggerUtils {

    /**
     * JMQ-DISPATCHER|方法名|错误信息
     */
    public static final String LOG_PREFIX = "JMQ-DISPATCHER|%s|%s";

    /**
     * 记录JSON操作TRACE日志，参数格式与log.trace一致
     *
     * @param log     日志对象
     * @param msg     消息模板
     * @param arg1    参数1
     * @param arg2    参数2
     */
    public static void trace(Logger log, String methodName, String msg, Object arg1, Object arg2) {
        log.trace(String.format(LOG_PREFIX, methodName, msg), arg1, arg2);
    }

    /**
     * 记录JSON操作TRACE日志，参数格式与log.trace一致
     *
     * @param log     日志对象
     * @param msg     消息模板
     * @param args    参数列表
     */
    public static void trace(Logger log, String methodName, String msg, Object... args) {
        log.trace(String.format(LOG_PREFIX, methodName, msg), args);
    }

    /**
     * 记录JSON操作TRACE日志，参数格式与log.trace一致
     *
     * @param log     日志对象
     * @param msg     消息模板
     * @param t       exception
     */
    public static void trace(Logger log, String methodName, String msg, Throwable t) {
        log.trace(String.format(LOG_PREFIX, methodName, msg), t);
    }

    /**
     * 记录JSON操作DEBUG日志，参数格式与log.debug一致
     *
     * @param log     日志对象
     * @param msg     消息模板
     * @param arg1    参数1
     * @param arg2    参数2
     */
    public static void debug(Logger log, String methodName, String msg, Object arg1, Object arg2) {
        log.debug(String.format(LOG_PREFIX, methodName, msg), arg1, arg2);
    }

    /**
     * 记录JSON操作DEBUG日志，参数格式与log.debug一致
     *
     * @param log     日志对象
     * @param msg     消息模板
     * @param args    参数列表
     */
    public static void debug(Logger log, String methodName, String msg, Object... args) {
        log.debug(String.format(LOG_PREFIX, methodName, msg), args);
    }

    /**
     * 记录JSON操作DEBUG日志，参数格式与log.debug一致
     *
     * @param log     日志对象
     * @param msg     消息模板
     * @param t       exception
     */
    public static void debug(Logger log, String methodName, String msg, Throwable t) {
        log.debug(String.format(LOG_PREFIX, methodName, msg), t);
    }

    /**
     * 记录JSON操作INFO日志，参数格式与log.info一致
     *
     * @param log     日志对象
     * @param msg     消息模板
     * @param arg1    参数1
     * @param arg2    参数2
     */
    public static void info(Logger log, String methodName, String msg, Object arg1, Object arg2) {
        log.info(String.format(LOG_PREFIX, methodName, msg), arg1, arg2);
    }

    /**
     * 记录JSON操作INFO日志，参数格式与log.info一致
     *
     * @param log     日志对象
     * @param msg     消息模板
     * @param args    参数列表
     */
    public static void info(Logger log, String methodName, String msg, Object... args) {
        log.info(String.format(LOG_PREFIX, methodName, msg), args);
    }

    /**
     * 记录JSON操作INFO日志，参数格式与log.info一致
     *
     * @param log     日志对象
     * @param msg     消息模板
     * @param t       exception
     */
    public static void info(Logger log, String methodName, String msg, Throwable t) {
        log.info(String.format(LOG_PREFIX, methodName, msg), t);
    }

    /**
     * 记录JSON操作WARN日志，参数格式与log.warn一致
     *
     * @param log     日志对象
     * @param msg     消息模板
     * @param arg1    参数1
     * @param arg2    参数2
     */
    public static void warn(Logger log, String methodName, String msg, Object arg1, Object arg2) {
        log.warn(String.format(LOG_PREFIX, methodName, msg), arg1, arg2);
    }

    /**
     * 记录JSON操作WARN日志，参数格式与log.warn一致
     *
     * @param log     日志对象
     * @param msg     消息模板
     * @param args    参数列表
     */
    public static void warn(Logger log, String methodName, String msg, Object... args) {
        log.warn(String.format(LOG_PREFIX, methodName, msg), args);
    }

    /**
     * 记录JSON操作WARN日志，参数格式与log.warn一致
     *
     * @param log     日志对象
     * @param msg     消息模板
     * @param t       exception
     */
    public static void warn(Logger log, String methodName, String msg, Throwable t) {
        log.warn(String.format(LOG_PREFIX, methodName, msg), t);
    }

    /**
     * 记录JSON操作错误日志，参数格式与log.error一致
     *
     * @param log     日志对象
     * @param msg     错误消息模板
     * @param arg1    参数1
     * @param arg2    参数2
     */
    public static void error(Logger log, String methodName, String msg, Object arg1, Object arg2) {
        log.error(String.format(LOG_PREFIX, methodName, msg), arg1, arg2);
    }

    /**
     * 记录JSON操作错误日志，参数格式与log.error一致
     *
     * @param log     日志对象
     * @param msg     错误消息模板
     * @param args    参数列表
     */
    public static void error(Logger log, String methodName, String msg, Object... args) {
        log.error(String.format(LOG_PREFIX, methodName, msg), args);
    }

    /**
     * 记录JSON操作错误日志，参数格式与log.error一致
     *
     * @param log     日志对象
     * @param msg     错误消息模板
     * @param t       exception
     */
    public static void error(Logger log, String methodName, String msg, Throwable t) {
        log.error(String.format(LOG_PREFIX, methodName, msg), t);
    }
}