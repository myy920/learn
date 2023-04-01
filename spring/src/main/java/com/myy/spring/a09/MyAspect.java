package com.myy.spring.a09;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class MyAspect {

    @Before("execution(* com.myy.spring.a09.MyService.foo())")
    public void before() {
        System.out.println("before");
    }

    @Before("execution(* com.myy.spring.a09.MyService.staticFoo())")
    public void before2() {
        System.out.println("before2");
    }

}
