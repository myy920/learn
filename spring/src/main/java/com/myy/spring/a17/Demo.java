package com.myy.spring.a17;

import com.myy.spring.common.Do;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.context.support.GenericApplicationContext;

import java.util.List;

public class Demo {

    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean(Target.class);
        context.registerBean(MyAspectJ.class);
        context.registerBean(AnnotationAwareAspectJAutoProxyCreator.class);

        context.refresh();

        context.getBeansOfType(Object.class).forEach((k, v) -> System.out.println(k + " --> " + v));

        System.out.println("=============");
        Target bean = context.getBean(Target.class);
        System.out.println(bean.getClass());
        bean.foo();
    }

}
