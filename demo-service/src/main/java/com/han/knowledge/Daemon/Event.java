package com.han.knowledge.Daemon;

import java.util.ArrayDeque;
import java.util.Date;
import java.util.Deque;
/**
 * Created by hp on 2017-03-23.
 */
public class Event {

    private Date date;
    private String event;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }


    public static void main(String[] args) {
        Deque<Event> deque = new ArrayDeque<Event>();
        WriterTask writer = new WriterTask(deque);
        for(int i = 0; i < 3; i++) {
            System.out.println("for");
            Thread thread = new Thread(writer);
            thread.start();
        }
        CleanerTask cleaner = new CleanerTask(deque);
        cleaner.start();
    }
}
