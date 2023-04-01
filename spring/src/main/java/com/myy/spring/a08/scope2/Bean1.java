package com.myy.spring.a08.scope2;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class Bean1 {

    @Lazy // 创建代理对象
    @Autowired
    public Bean2 bean2;

    @Autowired
    public Bean3 bean3;

    @Autowired
    public ObjectFactory<Bean4> bean4;

    @Autowired
    public ApplicationContext applicationContext;

    public Bean4 getBean4() {
        return bean4.getObject();
    }

    public Bean5 getBean5() {
        return applicationContext.getBean(Bean5.class);
    }

}
