package com.han.Metadata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.HashMap;

// 准备一个Class类 作为Demo演示
@Repository("repositoryName")
@Service("serviceName")
@EnableAsync
class MetaDemo extends HashMap<String, String> implements java.io.Serializable {
    private static class InnerClass {
    }

    @Autowired
    private String getName() {
        return "demo";
    }
}
