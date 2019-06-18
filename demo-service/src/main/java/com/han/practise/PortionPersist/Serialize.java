package com.han.practise.PortionPersist;


/**
 * @Author: hanyf
 * @Description:
 * @Date: Created by in 15:32 2017/8/30
 */
public class Serialize {

    public static void main(String[] args) {
        //基本工资1000元,绩效工资2500元
        Salary salary = new Salary(1000, 2500);
        //记录人员信息
        Person person = new Person("张三",salary);
        //HR系统持久化，并传递到计税系统
        SerializationUtils.writeObject(person);
    }
}
