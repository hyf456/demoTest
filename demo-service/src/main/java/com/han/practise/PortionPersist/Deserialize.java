package com.han.practise.PortionPersist;


/**
 * @Author: hanyf
 * @Description:
 * @Date: Created by in 15:39 2017/8/30
 */
public class Deserialize {

    public static void main(String[] args) {
        //技术系统反序列化,并打印信息
        Person person = (Person) SerializationUtils.readObject();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("姓名：" + person.getName());
        stringBuffer.append("\t 基本工资：" + person.getSalary().getBasePay());
        stringBuffer.append("\t 绩效工资：" + person.getSalary().getBonus());
        System.out.println(stringBuffer);
    }
}
