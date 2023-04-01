package com.myy.spring.a03;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

@Component
public class LifeCycleBean {

    public LifeCycleBean() {
        System.out.println("构造!");
    }

    //@Autowired
    @Resource
    private void autowired(@Value("${JAVA_HOME}") String home) {
        System.out.println("@Autowired注解标记的方法! 依赖注入 home=" + home);
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("@PostConstruct注解标记的方法! 初始化");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("@PreDestroy注解标记的方法! 销毁");
    }
}
