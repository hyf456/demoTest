/**
 * Project:      CommonEvent
 * FileName:     com.han.knowledge.Event.java
 * @Description: TODO
 * @author:      ligh4
 * @version      V1.0 
 * Createdate:   2015年3月16日 下午4:28:45
 * Copyright:    Copyright(C) 2014-2015
 * Company       Lenovo LTD.
 * All rights Reserved, Designed By Lenovo CIC.
 */
package com.han.knowledge.Event;

import com.han.knowledge.Util.IDGenerator;

/**
 * 类 com.han.knowledge.Event 的实现描述：TODO 类实现描述
 * 
 * @author @author hanyf 2017年3月23日下午4:42:40
 */
public class Event {
    private String id;
    private String type;
    private Object param;

    public Event(String type) {
        this.id = IDGenerator.gen();
        this.type = type;
    }

    public Event(String type, Object param) {
        this.id = IDGenerator.gen();
        this.type = type;
        this.param = param;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Object getParam() {
        return param;
    }
}
