package com.han.practise.PortionPersist;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @Author: hanyf
 * @Description: Person类
 * @Date: Created by in 15:30 2017/8/30
 */
public class Person implements Serializable{

    private static final long serialVersionUID = 108873074190880885L;

    //姓名
    private String name;
    //薪水
    private Salary salary;

    public Person(String name, Salary salary) {
        this.name = name;
        this.salary = salary;
    }

    //序列化委托方法
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();//告知JVM按照默认的规则写入对象，惯例的写法是写在第一句话里
        out.writeInt(salary.getBasePay());
    }

    //反序列化时委托方法
    private void readObject(ObjectInputStream in) throws IOException,ClassNotFoundException {
         in.defaultReadObject();//告知JVM按照默认规则读入对象，惯例的写法也是写在第一句话里
         salary = new Salary(in.readInt(),0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Salary getSalary() {
        return salary;
    }

    public void setSalary(Salary salary) {
        this.salary = salary;
    }
}
