/**
 * Project:      CommonEvent
 * FileName:     IEventFinishedCallback.java
 * @Description: TODO
 * @author:      ligh4
 * @version      V1.0 
 * Createdate:   2015年3月16日 下午4:34:04
 * Copyright:    Copyright(C) 2014-2015
 * Company       Lenovo LTD.
 * All rights Reserved, Designed By Lenovo CIC.
 */
package com.han.knowledge.Event;

/**
 * 类 IEventFinishedCallback 的实现描述：TODO 类实现描述
 * 
 * @author @author hanyf 2017年3月23日下午4:42:40
 */
public interface IEventConsumedCallback {
    public void onEventFinished(Event event, Object result);
}
