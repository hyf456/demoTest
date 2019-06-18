/**
 * Project:      CommonEvent
 * FileName:     EventService.java
 * @Description: TODO
 * @author:      ligh4
 * @version      V1.0 
 * Createdate:   2015年3月16日 下午4:42:40
 * Copyright:    Copyright(C) 2014-2015
 * Company       Lenovo LTD.
 * All rights Reserved, Designed By Lenovo CIC.
 */
package com.han.knowledge.Event;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 类 EventService 的实现描述：TODO 类实现描述
 * 
 * @author hanyf 2017年3月23日下午4:42:40
 */
public class EventService {

    /**
     * @author ligh4 2015年3月18日下午3:12:30
     * @param cfg properties like "handlerclassname = eventtype1,eventtype2"
     */
    public static void init(Properties cfg) {
        instance.loadHandlers(cfg);

        instance.eventsConsumeThread = new Thread(new Runnable() {

            @Override
            public void run() {
                instance.consumeEvents();
            }
        });

        instance.eventsConsumeThread.start();
    }

    public static void stop() {
        instance.stopped = true;
        instance.threadPool.shutdown();
    }

    public static void fireEvent(Event event) {

        instance.events.add(event);
    }

    public static void fireEvent(Event event, IEventConsumedCallback callback) {
        instance.eventCallbacks.put(event.getId(), callback);
        fireEvent(event);
    }

    public static void registerEventHandler(String eventType, IEventHandler handler) {
        List<IEventHandler> handlers = instance.eventHandlers.get(eventType);
        if (handlers == null) {
            handlers = new ArrayList<IEventHandler>();
            instance.eventHandlers.put(eventType, handlers);
        }

        handlers = instance.eventHandlers.get(eventType);
        handlers.add(handler);
    }

    private void consumeEvents() {
        while (!stopped) {
            if (!events.isEmpty()) {
                Event event = events.poll();
                Object result = null;
                List<IEventHandler> handlers = eventHandlers.get(event.getType());
                for (IEventHandler handler : handlers) {
                    result = handler.onEvent(event);
                }

                IEventConsumedCallback callback = eventCallbacks.get(event.getId());
                if (callback != null) {
                    eventCallbacks.remove(event.getId());
                    callback.onEventFinished(event, result);
                }
            }
        }
    }

    private void loadHandlers(Properties cfg) {
        if (cfg == null) {
            return;
        }

        Enumeration<Object> keys = cfg.keys();
        while (keys.hasMoreElements()) {
            String fullClassName = keys.nextElement().toString();
            String eventTypes = cfg.getProperty(fullClassName);
            Object instance = null;
            try {
                instance = Class.forName(fullClassName).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (instance != null) {

                StringTokenizer tokenizer = new StringTokenizer(eventTypes, ",");

                while (tokenizer.hasMoreTokens()) {
                    String eventType = tokenizer.nextToken();
                    eventType = eventType.trim();

                    registerEventHandler(eventType, (IEventHandler) instance);

                }
            }

        }
    }

    private ConcurrentLinkedQueue<Event>                      events         = new ConcurrentLinkedQueue<Event>();
    private ConcurrentHashMap<String, IEventConsumedCallback> eventCallbacks = new ConcurrentHashMap<String, IEventConsumedCallback>();
    private ConcurrentHashMap<String, List<IEventHandler>>    eventHandlers  = new ConcurrentHashMap<String, List<IEventHandler>>();
    private ThreadPoolExecutor                                threadPool     = new ThreadPoolExecutor(
                                                                                     4,
                                                                                     10,
                                                                                     5000,
                                                                                     TimeUnit.MILLISECONDS,
                                                                                     new LinkedBlockingQueue<Runnable>());              ;

    private boolean                                           stopped        = false;
    private Thread                                            eventsConsumeThread;

    private static EventService                               instance       = new EventService();

    private EventService() {
    }
}
