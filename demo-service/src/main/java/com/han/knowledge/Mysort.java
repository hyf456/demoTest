package com.han.knowledge;

/**
 * Created by hp on 2017-05-25.
 */
public class Mysort {
    int result;

    public void sort(int iRecordNum, int iType) {
        int x = 0;
        int y = 0;
        while (iRecordNum > 0) {
            if (iType == 0) {
                x = y + 2;
            } else {
                if (iType == 1) {
                    x = y + 5;
                } else {
                    x = y + 10;
                }
            }
            iRecordNum--;
            result = x;
        }
    }

    public int getResult() {
        return result;
    }

    public static void main(String[] args) {
        Mysort mysort = new Mysort();
//        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                int test = mysort.test(0, j);
                System.out.println(test);
            }
//        }
    }

    public int test(int i_count, int i_flag) {
        int i_temp = 0;
        while (i_count == 0) {
            if (0 == i_flag) {
                i_temp = i_count + 100;
                break;
            } else {
                if (1 == i_flag) {
                    i_temp = i_temp + 10;
                } else {
                    i_temp = i_temp + 20;
                }
            }
            i_count--;
        }
        return i_temp;
    }
}
