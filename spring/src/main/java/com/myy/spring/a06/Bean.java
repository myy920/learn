package com.myy.spring.a06;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class Bean implements BeanNameAware, ApplicationContextAware, InitializingBean {

    @Override
    public void setBeanName(String s) {
        System.out.println("beanName=" + s);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("applicationContext=" + applicationContext);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean回调");
    }

    @Autowired
    public void autowired(ApplicationContext applicationContext) {
        System.out.println("@Autowired注入applicationContext=" + applicationContext);
    }

    @PostConstruct
    public void init() {
        System.out.println("@PostConstruct初始化");
    }

    @PreDestroy
    public void preDestroy(){
        System.out.println("@PreDestroy销毁");
    }

}
