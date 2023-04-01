package com.myy.spring.a17;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class MyAspectJ {

    @Pointcut("execution(* com.myy.spring.a17.Target.foo())")
    public void pointcut() {

    }

    @Around(value = "pointcut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("before");
        Object res = pjp.proceed();
        System.out.println("after");
        return res;
    }

}
