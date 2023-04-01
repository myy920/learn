package com.myy.spring.a03;

import org.springframework.beans.factory.config.BeanPostProcessor;

import java.util.ArrayList;
import java.util.List;

public class MyBeanFactory {

    private List<BeanPostProcessor> processors = new ArrayList<>();

    public Object getBean() {
        for (BeanPostProcessor processor : processors) {
            processor.postProcessBeforeInitialization(null, "");
        }
        Object bean = new Object();
        System.out.println("构造");
        for (BeanPostProcessor processor : processors) {
            processor.postProcessAfterInitialization(null, "");
        }
        System.out.println("依赖注入");

        System.out.println("初始化");
        return bean;
    }

    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        processors.add(beanPostProcessor);
    }

}
