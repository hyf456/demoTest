package com.han.knowledge.FactoryClass;


/**
 * Created by hp on 2017-04-10.
 */
public class ParkFactory {
//    public static IPark CreatePark(String typeName)
//    {
////        #region 方法一
//        //load中是程序集的名称，也就是生成的DLL的名称
//        //CreateInstance后面的是类的完整名称，带命名空间
//        IPark park = Assembly.Load("ParkFactorys").CreateInstance(typeName) as IPark;
////        #region 方法二
//        //GetType的参数需是类的完整名称，带命名空间
//        Type type = Type.GetType(typeName, true);
//        IPark park = Activator.CreateInstance(type) as IPark;
//        return park;
//    }

    public static IPark CreatePark(String className) {
        IPark fruit = null;
        try {
            fruit = (IPark) Class.forName(className).newInstance();
            return fruit;
        } catch (InstantiationException e) {
            throw new RuntimeException("实例化异常！", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("这个实体类不存在！", e);
        }
    }
}
