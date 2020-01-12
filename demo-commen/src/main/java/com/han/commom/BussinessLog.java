package com.yestae.base.user.service.annotion;


import com.yestae.base.user.service.util.dic.AbstractDictMap;
import com.yestae.base.user.service.util.dic.dicmap.SystemDict;

import java.lang.annotation.*;

/**
 * @Description 标记需要做业务日志的方法
 * @Date 2019/8/1 10:56
 * @Author hanyf
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface BussinessLog {

    /**
     * 业务的名称,例如:"修改菜单"
     */
    String value() default "";

    /**
     * 被修改的实体的唯一标识,例如:菜单实体的唯一标识为"id"
     */
    String key() default "id";

    /**
     * 字典(用于查找key的中文名称和字段的中文名称)
     */
    Class<? extends AbstractDictMap> dict() default SystemDict.class;
}