package com.han.knowledge.Daemon;

import java.util.Date;
import java.util.Deque;

/**
 * Created by hp on 2017-03-23.
 */
public class CleanerTask extends Thread {

    private Deque<Event> deque;

    public CleanerTask(Deque<Event> deque) {
        System.out.println("CleanerTask");
        this.deque = deque;
        //设置为守护进程
        setDaemon(true);
    }

    @Override
    public void run() {
        while(true) {
//            System.out.println("run");
            Date date = new Date();
            clean(date);
        }
    }

    private void clean(Date date) {
//        System.out.println("clean");
        long difference = 0;
        boolean delete = false;

//        System.out.println(deque.size());
        if(deque.size() == 0) {
            return;
        }

        do {
            Event e = deque.getLast();
            difference = date.getTime() - e.getDate().getTime();
            if(difference > 10000) {
                System.out.println("cleaner " + e.getEvent());
                System.out.println(difference);
                deque.removeLast();
                delete = true;
            }
        }while(difference > 10000);

        if(delete) {
            System.out.println("cleaner : the size of the deque " + deque.size());
        }
    }

    public Deque<Event> getDeque() {
        return deque;
    }

    public void setDeque(Deque<Event> deque) {
        this.deque = deque;
    }
}
