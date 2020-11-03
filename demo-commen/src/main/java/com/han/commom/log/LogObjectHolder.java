// package com.yestae.base.user.service.log;
//
// import com.han.commom.util.SpringContextHolder;
// import org.springframework.context.annotation.Scope;
// import org.springframework.stereotype.Component;
// import org.springframework.web.context.WebApplicationContext;
//
// /**
//  * @Description 被修改的bean临时存放的地方
//  * @Date 2019/8/1 11:04
//  * @Author hanyf
//  */
// @Component
// @Scope(scopeName = WebApplicationContext.SCOPE_SESSION)
// public class LogObjectHolder {
//
//     private Object object = null;
//
//     public void set(Object obj) {
//         this.object = obj;
//     }
//
//     public Object get() {
//         return object;
//     }
//
//     public static LogObjectHolder me() {
//         LogObjectHolder bean = SpringContextHolder.getBean(LogObjectHolder.class);
//         return bean;
//     }
// }
