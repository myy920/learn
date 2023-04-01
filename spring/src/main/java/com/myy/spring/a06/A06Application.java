package com.myy.spring.a06;

import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;

public class A06Application {

    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();
        //context.registerBean("bean1", Bean1.class);
        //context.registerBean("config1", Config1.class);
        context.registerBean("config2", Config2.class);
        context.registerBean(AutowiredAnnotationBeanPostProcessor.class);
        context.registerBean(CommonAnnotationBeanPostProcessor.class);
        context.registerBean(ConfigurationClassPostProcessor.class);
        context.refresh();
        context.getBeansOfType(Object.class).forEach((k, v) -> System.out.println(k + " --> " + v));
        context.close();
    }

}
