package com.han.aspectJ;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author:
 * @Description: aop切面类
 * @Date: 2018/4/16 15:00
 */
@Aspect //必须使用@AspectJ标注，这样class DemoAspect就等同于 aspect DemoAspect了
public class TimeTraceAspect {
    public static final Logger LOGGER = LoggerFactory.getLogger(TimeTraceAspect.class);

    /*
@Pointcut：pointcut也变成了一个注解，这个注解是针对一个函数的，比如此处的logForActivity()
其实它代表了这个pointcut的名字。如果是带参数的pointcut，则把参数类型和名字放到
代表pointcut名字的logForActivity中，然后在@Pointcut注解中使用参数名。
基本和以前一样，只是写起来比较奇特一点。后面我们会介绍带参数的例子
*/
    @Pointcut("execution(@com.han.aspectJ.TimeTrace * *(..))")
    public void myPointCut (){

    }

    /*
@Before：这就是Before的advice，对于after，after -returning，和after-throwing。对于的注解格式为
@After，@AfterReturning，@AfterThrowing。Before后面跟的是pointcut名字，然后其代码块由一个函数来实现。比如此处的log。
*/
    @Before("myPointCut()")
    public void log(JoinPoint joinPoint){
        //对于使用Annotation的AspectJ而言，JoinPoint就不能直接在代码里得到多了，而需要通过
        //参数传递进来。
        LOGGER.error(joinPoint.toShortString());
    }

    @Around("myPointCut()")
    public Object dealPoint(ProceedingJoinPoint point) throws Throwable {
        // 方法执行前先记录时间
        long start=System.currentTimeMillis();
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        // 获取注解
        TimeTrace annotation = methodSignature.getMethod().getAnnotation(TimeTrace.class);
        String value = annotation.value();

        // 执行原方法体
        Object proceed = point.proceed();

        // 方法执行完成后，记录时间，打印日志
        long end = System.currentTimeMillis();
        StringBuffer stringBuffer = new StringBuffer();
        if (proceed instanceof Boolean){
            // 返回的是boolean
            if ((Boolean)proceed){
                stringBuffer.append(value)
                        .append("成功，耗时：")
                        .append(end - start);
            }else{
                stringBuffer.append(value)
                        .append("失败，耗时：")
                        .append(end - start);
            }
        }
        LOGGER.error(stringBuffer.toString());
        return proceed;
    }
}
