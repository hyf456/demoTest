package com.han.servlet;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: hanyf
 * @Description: servlet3异步优化
 * @Date: 2018/7/20 10:38
 */
@RestController
@RequestMapping(value = "/servletController")
public class ServletController {

    @GetMapping("/book")
    public void getBook(HttpServletRequest request,
                        @RequestParam(value="skuId") final Long skuId,
                        @RequestParam(value = "cat1") final Integer cat1,
                        @RequestParam(value = "cat2") final Integer cat2) throws Exception {


    }

    public void submitFuture(final HttpServletRequest request, final Callable<Object> task) {
        final String requestURI = request.getRequestURI();
        final Map<String, String[]> parameterMap = request.getParameterMap();
        //开启异步上下文
        final AsyncContext asyncContext = request.startAsync();
        asyncContext.getRequest().setAttribute("uri", requestURI);
        asyncContext.getRequest().setAttribute("params", parameterMap);
        int asyncTimeoutInSeconds = 0;
        asyncContext.setTimeout(asyncTimeoutInSeconds * 1000);
        AsyncListener asyncListener = new AsyncListener() {
            @Override
            public void onComplete(AsyncEvent asyncEvent) throws IOException {

            }

            @Override
            public void onTimeout(AsyncEvent asyncEvent) throws IOException {

            }

            @Override
            public void onError(AsyncEvent asyncEvent) throws IOException {

            }

            @Override
            public void onStartAsync(AsyncEvent asyncEvent) throws IOException {

            }
        };
        if (null != asyncListener) {
            asyncContext.addListener(asyncListener);
        }
        ExecutorService executor = Executors.newSingleThreadExecutor();
        //提交任务给线程池
        executor.submit((Runnable) new CanceledCallable(asyncContext));

    }

    /**
     * @Author: hanyf
     * @Description: 线程池初始化
     * @Date: 2018/7/20 15:28
     */
    public void afterPropertiesSet() throws Exception {
    }
}
