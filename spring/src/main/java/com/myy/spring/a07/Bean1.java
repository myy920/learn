package com.myy.spring.a07;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class Bean1 implements BeanNameAware, InitializingBean, DisposableBean {

    @Autowired
    public void autowired(){
        System.out.println("@Autowired依赖注入");
    }

    @Override
    public void setBeanName(String s) {
        System.out.println("beanNameAware调用,beanName=" + s);
    }

    @PostConstruct
    public void init() {
        System.out.println("@PostConstruct初始化");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean初始化");
    }

    public void initMethod() {
        System.out.println("initMethod初始化");
    }

    @PreDestroy
    public void preDestroy(){
        System.out.println("@PreDestroy销毁");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean销毁");
    }

    public void destroyMethod(){
        System.out.println("destroyMethod销毁");
    }
}


