package com.han.servlet;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Objects;
import java.util.Properties;

/**
 * @Author: hanyf
 * @Description: WatchService实现文件变更监听
 * @Date: 2018/7/25 11:01
 */
public class WatchServiceDemo {
    static String filename;
    private static ClassPathResource classPathResource = null;
    private static WatchService watchService = null;

    private static Properties properties = null;

//    static {
//        try {
//            filename = "application.properties";
//            classPathResource = newcache ClassPathResource(filename);
//            //监听filename所在目录下的文件修改、删除事件
//            watchService = FileSystems.getDefault().newWatchService();
//            Paths.get(classPathResource.getFile().getParent()).register(watchService,
//                    StandardWatchEventKinds.ENTRY_MODIFY,
//                    StandardWatchEventKinds.ENTRY_DELETE);
//            properties = PropertiesLoaderUtils.loadProperties(classPathResource);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        //启动一个线程监听内容变化，并重新载入配置
//        Thread watchThread = newcache Thread(() -> {
//            System.out.println("线程名称:" + Thread.currentThread().getName());
//            while (true) {
//                try {
//                    WatchKey watchKey = watchService.take();
//                    for (WatchEvent<?> event : watchKey.pollEvents()) {
//                        if (Objects.equals(event.context().toString(), filename)) {
//                            properties = PropertiesLoaderUtils.loadProperties(classPathResource);
//                            break;
//                        }
//                    }
//                    watchKey.reset();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        watchThread.setDaemon(true);
//        watchThread.start();
//
//        Runtime.getRuntime().addShutdownHook(newcache Thread(() -> {
//            try {
//                watchService.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }));
//    }

    public static void main(String[] args) throws IOException {
        filename = "application.properties";
        classPathResource = new ClassPathResource(filename);
        //监听filename所在目录下的文件修改、删除事件
        watchService = FileSystems.getDefault().newWatchService();
        Paths.get(classPathResource.getFile().getParent()).register(watchService,
                StandardWatchEventKinds.ENTRY_MODIFY,
                StandardWatchEventKinds.ENTRY_DELETE);

        while (true) {
            try {
                WatchKey watchKey = watchService.take();
                for (WatchEvent<?> event : watchKey.pollEvents()) {
                    if (Objects.equals(event.context().toString(), filename)) {
                        properties = PropertiesLoaderUtils.loadProperties(classPathResource);
                        break;
                    }
                    System.out.println(event.context() + " comes to " + event.kind());
                }
                boolean reset = watchKey.reset();
                if (!reset) {
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
