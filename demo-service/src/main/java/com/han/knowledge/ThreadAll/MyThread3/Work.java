package com.han.knowledge.ThreadAll.MyThread3;

/**
 * Created by hp on 2017-04-26.
 */
public class Work {

    public void process(Data data, Integer[] numbers) {
        for (int n : numbers)
        {
            data.value += n;
        }
    }
}
