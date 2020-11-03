// package com.yestae.base.user.service.aop;
//
// import com.yestae.base.user.service.annotion.BussinessLog;
// import com.yestae.base.user.service.log.LogObjectHolder;
// import com.yestae.base.user.service.util.HttpKit;
// import com.yestae.base.user.service.util.UserUtil;
// import com.yestae.base.user.service.util.dic.AbstractDictMap;
// import com.yestae.base.user.service.util.dic.Contrast;
// import lombok.extern.slf4j.Slf4j;
// import org.aspectj.lang.ProceedingJoinPoint;
// import org.aspectj.lang.Signature;
// import org.aspectj.lang.annotation.Around;
// import org.aspectj.lang.annotation.Aspect;
// import org.aspectj.lang.annotation.Pointcut;
// import org.aspectj.lang.reflect.MethodSignature;
// import org.springframework.stereotype.Component;
//
// import java.lang.reflect.Method;
// import java.util.Map;
//
// /**
//  * @Description 日志记录
//  * @Date 2019/8/1 10:54
//  * @Author hanyf
//  */
// @Slf4j
// @Aspect
// @Component
// public class LogAop {
//
//     @Pointcut(value = "@annotation(com.yestae.base.user.service.annotion.BussinessLog)")
//     public void cutService() {
//     }
//
//     @Around("cutService()")
//     public Object recordSysLog(ProceedingJoinPoint point) throws Throwable {
//
//         //先执行业务
//         Object result = point.proceed();
//
//         try {
//             handle(point);
//         } catch (Exception e) {
//             log.error("日志记录出错!", e);
//         }
//
//         return result;
//     }
//
//     private void handle(ProceedingJoinPoint point) throws Exception {
//
//         //获取拦截的方法名
//         Signature sig = point.getSignature();
//         MethodSignature msig = null;
//         if (!(sig instanceof MethodSignature)) {
//             throw new IllegalArgumentException("该注解只能用于方法");
//         }
//         msig = (MethodSignature) sig;
//         Object target = point.getTarget();
//         Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
//         String methodName = currentMethod.getName();
//
//         //如果当前用户未登录，不做日志
//         Long currentUserId = UserUtil.getCurrentUserId();
//         if (null == currentUserId) {
//             return;
//         }
//
//         // 获取拦截方法的参数
//         String className = point.getTarget().getClass().getName();
//         Object[] params = point.getArgs();
//
//         // 获取操作名称
//         BussinessLog annotation = currentMethod.getAnnotation(BussinessLog.class);
//         String bussinessName = annotation.value();
//         String key = annotation.key();
//         Class dictClass = annotation.dict();
//
//         StringBuilder sb = new StringBuilder();
//         for (Object param : params) {
//             sb.append(param);
//             sb.append(" & ");
//         }
//
//         //如果涉及到修改,比对变化
//         String msg;
//         if (bussinessName.indexOf("修改") != -1 || bussinessName.indexOf("编辑") != -1) {
//             Object obj1 = LogObjectHolder.me().get();
//             Map<String, String> obj2 = HttpKit.getRequestParameters();
//             msg = Contrast.contrastObj(dictClass, key, obj1, obj2);
//         } else {
//             Map<String, String> parameters = HttpKit.getRequestParameters();
//             AbstractDictMap dictMap = (AbstractDictMap) dictClass.newInstance();
//             msg = Contrast.parseMutiKey(dictMap, key, parameters);
//         }
//
//         // LogManager.me().executeLog(LogTaskFactory.bussinessLog(currentUserId, bussinessName, className, methodName, msg));
//     }
// }
