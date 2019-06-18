package com.han.practise.PortionPersist;

import java.io.*;

/**
 * @Author: hanyf
 * @Description:
 * @Date: Created by in 15:43 2017/8/30
 */
public class SerializationUtils {

    private static String FILE_NAME = "D:/practise/obj.txt";

    // 序列化
    public static void writeObject(Serializable s) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
            oos.writeObject(s);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 反序列化
    public static Object readObject() {
        Object obj = null;
        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(FILE_NAME));
            obj = input.readObject();
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
}
