package com.myy.spring.a09.a18;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.context.support.GenericApplicationContext;

public class Demo {

    public static void main(String[] args) {
        // 干净的容器
        GenericApplicationContext context = new GenericApplicationContext();
        // 注册Aspect注解bean后置处理器
        context.registerBean(AnnotationAwareAspectJAutoProxyCreator.class);
        // 注册切面类
        context.registerBean(MyAspect.class);
        // 注册目标类
        context.registerBean(Target.class);
        // 容器刷新
        context.refresh();
        // 获取目标并执行方法
        Target target = context.getBean(Target.class);
        target.foo("鸡你太美!");
        System.out.println("---分割线---");
        System.out.println("target动态代理类=" + target.getClass());

    }

    // 标记用的注解
    @interface Flag {
    }

    // 切面类
    @Aspect
    static class MyAspect {

        @Pointcut("@annotation(com.myy.spring.a09.a18.Demo.Flag)")
        public void pointcut() {

        }

        @Before("pointcut()") // AspectJMethodBeforeAdvice
        public void before() {
            System.out.println("@Before...");
        }

        @After("pointcut()") // AspectJAfterAdvice
        public void after() {
            System.out.println("@After...");
        }

        @AfterReturning("pointcut()") // AspectJAfterReturningAdvice
        public void afterReturning() {
            System.out.println("@AfterReturning...");
        }

        @AfterThrowing("pointcut()") // AspectJAfterThrowingAdvice
        public void afterThrowing() {
            System.out.println("@AfterThrowing...");
        }

        @Around("pointcut()")// AspectJAroundAdvice
        public Object around(ProceedingJoinPoint pjp) throws Throwable {
            System.out.println("@Around...执行前");
            Object result = pjp.proceed();
            System.out.println("@Around...执行后");
            return result;
        }

    }

    static class Target {

        @Flag
        public String foo(String param) {
            System.out.println("foo方法执行了,参数param=" + param);
            return "foo方法执行后返回结果:" + param;
        }

    }


}
