package com.han.aspectJ;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author:
 * @Description: 自定义注解
 * @Date: 2018/4/16 14:52
 */
//第一个@Target表示这个注解只能给函数使用
//第二个@Retention表示注解内容需要包含的Class字节码里，属于运行时需要的。
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TimeTrace {//@interface用于定义一个注解。
    String value();
}
