package com.han.commom.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description SpelUtils工具类使用示例
 * @ClassName SpelUtilsTest
 * @Author hanyunfei1
 * @Date 2025/8/26 21:20
 * @Version 1.0
 **/
public class SpelUtilsLocalTest {

    public static void main(String[] args) {
        // 创建测试对象
        Person person = new Person("张三", 30);
        person.setAddress(new Address("北京市", "朝阳区"));
        person.setSkills(Arrays.asList("Java", "Spring", "SpEL"));

        // 1. 基本表达式使用
        System.out.println("=== 基本表达式使用 ===");
        // 获取属性值
        String name = SpelUtils.getValue(person, "name", String.class);
        System.out.println("name: " + name);

        // 获取嵌套属性值
        String city = SpelUtils.getValue(person, "address.city", String.class);
        System.out.println("city: " + city);

        // 获取集合元素
        String firstSkill = SpelUtils.getValue(person, "skills[0]", String.class);
        System.out.println("firstSkill: " + firstSkill);

        // 使用方法
        Integer nameLength = SpelUtils.getValue(person, "name.length()", Integer.class);
        System.out.println("nameLength: " + nameLength);

        // 2. 模板表达式使用
        System.out.println("\n=== 模板表达式使用 ===");
        String greeting = SpelUtils.getValueFromTemplate(person, "你好，#{name}！你今年#{age}岁了。");
        System.out.println(greeting);

        // 3. 设置属性值
        System.out.println("\n=== 设置属性值 ===");
        SpelUtils.setValue(person, "name", "李四");
        System.out.println("修改后的name: " + person.getName());

        // 4. 布尔表达式评估
        System.out.println("\n=== 布尔表达式评估 ===");
        boolean isAdult = SpelUtils.evaluateBoolean(person, "age >= 18");
        System.out.println("isAdult: " + isAdult);

        // 5. 使用变量
        System.out.println("\n=== 使用变量 ===");
        Map<String, Object> variables = new HashMap<>();
        variables.put("minAge", 18);
        variables.put("maxAge", 60);
        boolean isWorkingAge = Boolean.TRUE.equals(SpelUtils.getValue(person, "age >= #minAge and age <= #maxAge", variables, Boolean.class));
        System.out.println("isWorkingAge: " + isWorkingAge);

        // 6. 复杂表达式
        System.out.println("\n=== 复杂表达式 ===");
        // 字符串操作
        String upperName = SpelUtils.getValue(person, "name.toUpperCase()", String.class);
        System.out.println("upperName: " + upperName);

        // 集合操作
        Integer skillCount = SpelUtils.getValue(person, "skills.size()", Integer.class);
        System.out.println("skillCount: " + skillCount);

        // 条件表达式
        String ageGroup = SpelUtils.getValue(person, "age > 60 ? '老年' : (age > 40 ? '中年' : '青年')", String.class);
        System.out.println("ageGroup: " + ageGroup);

        // 7. 模板表达式与变量结合
        System.out.println("\n=== 模板表达式与变量结合 ===");
        variables.put("title", "工程师");
        String introduction = SpelUtils.getValueFromTemplate(person, "#{name}是一名#{#title}，住在#{address.city}#{address.district}。", variables);
        System.out.println(introduction);
    }

    /**
     * 测试用Person类
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Person {
        private String name;
        private int age;
        private Address address;
        private List<String> skills;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

    /**
     * 测试用Address类
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Address {
        private String city;
        private String district;
    }
}