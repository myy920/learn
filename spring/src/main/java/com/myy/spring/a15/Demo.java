package com.myy.spring.a15;

import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

public class Demo {

    public static void main(String[] args) throws Exception {
        // 1.切点
        AspectJExpressionPointcut pointcut1 = new AspectJExpressionPointcut();
        pointcut1.setExpression("execution(* foo())");
        AspectJExpressionPointcut pointcut2 = new AspectJExpressionPointcut();
        pointcut2.setExpression("@annotation(com.myy.spring.a15.Anno)");
        NameMatchMethodPointcut pointcut3 = new NameMatchMethodPointcut();
        pointcut3.setMappedName("foo");


        // 2.通知
        MethodInterceptor advice1 = invocation -> {
            System.out.println("advice1 before");
            Object res = invocation.proceed(); // 调用目标
            System.out.println("advice1 after");
            return res;
        };
        MethodInterceptor advice2 = invocation -> {
            System.out.println("advice2 before");
            Object res = invocation.proceed(); // 调用目标
            System.out.println("advice2 after");
            return res;
        };
        MethodInterceptor advice3 = invocation -> {
            System.out.println("advice3 before");
            Object res = invocation.proceed(); // 调用目标
            System.out.println("advice3 after");
            return res;
        };
        // 准备切面
        DefaultPointcutAdvisor advisor1 = new DefaultPointcutAdvisor(pointcut1, advice1);
        DefaultPointcutAdvisor advisor2 = new DefaultPointcutAdvisor(pointcut2, advice2);
        DefaultPointcutAdvisor advisor3 = new DefaultPointcutAdvisor(pointcut3, advice3);
        Target target = new Target();
        ProxyFactory factory = new ProxyFactory();
        factory.setProxyTargetClass(false);
        factory.setTarget(target);
        factory.addAdvisor(advisor1);
        factory.addAdvisor(advisor2);
        factory.addAdvisor(advisor3);

        FooBar proxy = (FooBar) factory.getProxy();
        proxy.foo();
        System.out.println("----");
        proxy.bar();

        System.out.println(proxy.getClass());
        System.in.read();
    }


}
