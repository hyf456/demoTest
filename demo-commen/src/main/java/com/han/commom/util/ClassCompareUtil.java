package com.yestae.base.user.service.util;

import com.yestae.base.user.model.util.NumberUtil;
import lombok.extern.slf4j.Slf4j;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 两个对象比较属性
 * @Date 2019/9/11 14:20
 * @Author hanyf
 */
@Slf4j
public class ClassCompareUtil {

    /**
     * 比较两个实体属性值，返回一个boolean,true则表时两个对象中的属性值有差异
     *
     * @param oldObject 进行属性比较的对象1
     * @param newObject 进行属性比较的对象2
     * @return 属性差异比较结果boolean
     */
    public static boolean compareObject(Object oldObject, Object newObject) {
        Map<String, Map<String, Object>> resultMap = compareFields(oldObject, newObject);

        if (resultMap.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 比较两个实体属性值，返回一个map以有差异的属性名为key，value为一个Map分别存oldObject,newObject此属性名的值
     *
     * @param oldObject 进行属性比较的对象1
     * @param newObject 进行属性比较的对象2
     * @return 属性差异比较结果map
     */
    @SuppressWarnings("rawtypes")
    public static Map<String, Map<String, Object>> compareFields(Object oldObject, Object newObject) {
        Map<String, Map<String, Object>> map = null;

        try {
            /**
             * 只有两个对象都是同一类型的才有可比性
             */
            if (oldObject.getClass() == newObject.getClass()) {
                map = new HashMap<String, Map<String, Object>>();

                Class clazz = oldObject.getClass();
                //获取object的所有属性
                PropertyDescriptor[] pds = Introspector.getBeanInfo(clazz, Object.class).getPropertyDescriptors();

                for (PropertyDescriptor pd : pds) {
                    //遍历获取属性名
                    String name = pd.getName();

                    //获取属性的get方法
                    Method readMethod = pd.getReadMethod();

                    // 在oldObject上调用get方法等同于获得oldObject的属性值
                    Object oldValue = readMethod.invoke(oldObject);
                    // 在newObject上调用get方法等同于获得newObject的属性值
                    Object newValue = readMethod.invoke(newObject);

                    if (oldValue instanceof List) {
                        continue;
                    }

                    if (newValue instanceof List) {
                        continue;
                    }

                    if (oldValue instanceof Timestamp) {
                        oldValue = new Date(((Timestamp) oldValue).getTime());
                    }

                    if (newValue instanceof Timestamp) {
                        newValue = new Date(((Timestamp) newValue).getTime());
                    }

                    if (oldValue == null && newValue == null) {
                        continue;
                    } else if (oldValue == null && newValue != null) {
                        Map<String, Object> valueMap = new HashMap<String, Object>();
                        valueMap.put("oldValue", oldValue);
                        valueMap.put("newValue", newValue);

                        map.put(name, valueMap);

                        continue;
                    }

                    if (!oldValue.equals(newValue)) {// 比较这两个值是否相等,不等就可以放入map了
                        Map<String, Object> valueMap = new HashMap<String, Object>();
                        valueMap.put("oldValue", oldValue);
                        valueMap.put("newValue", newValue);

                        map.put(name, valueMap);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    /**
     * 比较两个实体属性值，返回一个boolean,true则表时两个对象中的属性值有差异
     *
     * @param oldObject        进行属性比较的对象1
     * @param newObject        进行属性比较的对象2
     * @param compareFiledList 需要进行比较的字段
     * @return 属性差异比较结果boolean
     */
    public static boolean compareObject(Object oldObject, Object newObject, List<String> compareFiledList) {
        Map<String, Map<String, Object>> resultMap = compareFields(oldObject, newObject, compareFiledList);

        if (resultMap.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 比较两个实体属性值，返回一个map以有差异的属性名为key，value为一个Map分别存oldObject,newObject此属性名的值
     *
     * @param oldObject        进行属性比较的对象1
     * @param newObject        进行属性比较的对象2
     * @param compareFiledList 需要进行比较的字段
     * @return 属性差异比较结果map
     */
    @SuppressWarnings("rawtypes")
    public static Map<String, Map<String, Object>> compareFields(Object oldObject, Object newObject, List<String> compareFiledList) {
        Map<String, Map<String, Object>> map = null;
        Long id = null;
        try {
            /**
             * 只有两个对象都是同一类型的才有可比性
             */
            if (oldObject.getClass() == newObject.getClass()) {
                map = new HashMap<String, Map<String, Object>>();
                Class clazz = oldObject.getClass();
                //获取object的所有属性
                PropertyDescriptor[] pds = Introspector.getBeanInfo(clazz, Object.class).getPropertyDescriptors();


                for (PropertyDescriptor pd : pds) {
                    //遍历获取属性名
                    String name = pd.getName();

                    if (compareFiledList.contains(name)) {
                        //获取属性的get方法
                        Method readMethod = pd.getReadMethod();

                        // 在oldObject上调用get方法等同于获得oldObject的属性值
                        Object oldValue = readMethod.invoke(oldObject);
                        // 在newObject上调用get方法等同于获得newObject的属性值
                        Object newValue = readMethod.invoke(newObject);

                        if (oldValue instanceof List) {
                            continue;
                        }

                        if (newValue instanceof List) {
                            continue;
                        }

                        if (oldValue instanceof Timestamp) {
                            oldValue = new Date(((Timestamp) oldValue).getTime());
                        }

                        if (newValue instanceof Timestamp) {
                            newValue = new Date(((Timestamp) newValue).getTime());
                        }

                        if (oldValue == null && newValue == null) {
                            continue;
                        } else if (oldValue == null && newValue != null) {
                            Map<String, Object> valueMap = new HashMap<String, Object>();
                            valueMap.put("oldValue", oldValue);
                            valueMap.put("newValue", newValue);

                            map.put(name, valueMap);
                            continue;
                        }

                        if (!oldValue.equals(newValue)) {// 比较这两个值是否相等,不等就可以放入map了
                            Map<String, Object> valueMap = new HashMap<String, Object>();
                            valueMap.put("oldValue", oldValue);
                            valueMap.put("newValue", newValue);

                            map.put(name, valueMap);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    private static Long getId(Class clazz) {
        Method methodId = getPropertyDescriptor(clazz, "id").getReadMethod();
        Long id = null;
        try {
            id = NumberUtil.getLong(methodId.invoke(clazz, new Object[]{}));
        } catch (Exception e) {
            log.error("compareFields method error{}", e);
        }
        return id;
    }

    @SuppressWarnings("unchecked")
    public static PropertyDescriptor getPropertyDescriptor(Class clazz, String propertyName) {
        StringBuffer sb = new StringBuffer();//构建一个可变字符串用来构建方法名称
        Method setMethod = null;
        Method getMethod = null;
        PropertyDescriptor pd = null;
        try {
            Field f = clazz.getDeclaredField(propertyName);//根据字段名来获取字段
            if (f != null) {
                //构建方法的后缀
                String methodEnd = propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
                sb.append("set" + methodEnd);//构建set方法
                setMethod = clazz.getDeclaredMethod(sb.toString(), new Class[]{f.getType()});
                sb.delete(0, sb.length());//清空整个可变字符串
                sb.append("get" + methodEnd);//构建get方法
                //构建get 方法
                getMethod = clazz.getDeclaredMethod(sb.toString(), new Class[]{});
                //构建一个属性描述器 把对应属性 propertyName 的 get 和 set 方法保存到属性描述器中
                pd = new PropertyDescriptor(propertyName, getMethod, setMethod);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return pd;
    }
}
