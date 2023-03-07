package com.myy.spring5.a18;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.junit.Test;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJAfterAdvice;
import org.springframework.aop.aspectj.AspectJAfterReturningAdvice;
import org.springframework.aop.aspectj.AspectJAfterThrowingAdvice;
import org.springframework.aop.aspectj.AspectJAroundAdvice;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.aspectj.AspectJMethodBeforeAdvice;
import org.springframework.aop.aspectj.SingletonAspectInstanceFactory;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.ReflectiveMethodInvocation;
import org.springframework.aop.interceptor.ExposeInvocationInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.core.annotation.Order;
import org.springframework.lang.Nullable;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 静态通知
 */
public class A18 {

    public static void main(String[] args) throws Throwable {
        Target target = new Target();

        SingletonAspectInstanceFactory factory = new SingletonAspectInstanceFactory(new Aspect());
        // 1.高级切面转换成低级切面
        List<Advisor> list = new ArrayList<>();
        for (Method method : Aspect.class.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Before.class)) {
                // 解析切点
                String expression = method.getAnnotation(Before.class).value();
                AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
                pointcut.setExpression(expression);
                // 通知类
                Advice advice = new AspectJMethodBeforeAdvice(method, pointcut, factory);
                // 切面(=切点 + 通知)
                Advisor advisor = new DefaultPointcutAdvisor(pointcut, advice);
                list.add(advisor);
            } else if (method.isAnnotationPresent(After.class)) {
                // 解析切点
                String expression = method.getAnnotation(After.class).value();
                AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
                pointcut.setExpression(expression);
                // 通知类
                Advice advice = new AspectJAfterAdvice(method, pointcut, factory);
                // 切面(=切点 + 通知)
                Advisor advisor = new DefaultPointcutAdvisor(pointcut, advice);
                list.add(advisor);
            } else if (method.isAnnotationPresent(AfterReturning.class)) {
                // 解析切点
                String expression = method.getAnnotation(AfterReturning.class).value();
                AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
                pointcut.setExpression(expression);
                // 通知类
                Advice advice = new AspectJAfterReturningAdvice(method, pointcut, factory);
                // 切面(=切点 + 通知)
                Advisor advisor = new DefaultPointcutAdvisor(pointcut, advice);
                list.add(advisor);
            } else if (method.isAnnotationPresent(AfterThrowing.class)) {
                // 解析切点
                String expression = method.getAnnotation(AfterThrowing.class).value();
                AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
                pointcut.setExpression(expression);
                // 通知类
                Advice advice = new AspectJAfterThrowingAdvice(method, pointcut, factory);
                // 切面(=切点 + 通知)
                Advisor advisor = new DefaultPointcutAdvisor(pointcut, advice);
                list.add(advisor);
            } else if (method.isAnnotationPresent(Around.class)) {
                // 解析切点
                String expression = method.getAnnotation(Around.class).value();
                AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
                pointcut.setExpression(expression);
                // 通知类
                Advice advice = new AspectJAroundAdvice(method, pointcut, factory);
                // 切面(=切点 + 通知)
                Advisor advisor = new DefaultPointcutAdvisor(pointcut, advice);
                list.add(advisor);
            }
        }
        list.forEach(System.out::println);

        // 2.通知统一转换为环绕通知 MethodInterceptor
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(target);
        proxyFactory.addAdvice(ExposeInvocationInterceptor.INSTANCE);
        proxyFactory.addAdvisors(list);
        List<Object> methodInterceptors =
                proxyFactory.getInterceptorsAndDynamicInterceptionAdvice(Target.class.getMethod("foo"),
                        Target.class);
        methodInterceptors.forEach(System.out::println);

        // 3.创建并执行调用链
        ReflectiveMethodInvocation methodInvocation = newReflectiveMethodInvocation(null, target,
                Target.class.getMethod("foo"), new Object[0], Target.class, methodInterceptors);
        ThreadLocal<ReflectiveMethodInvocation> sThreadLocal = ThreadLocal.withInitial(() -> methodInvocation);

        methodInvocation.proceed();

    }

    private static ReflectiveMethodInvocation newReflectiveMethodInvocation(Object proxy, @Nullable Object target,
                                                                            Method method,
                                                                            @Nullable Object[] arguments,
                                                                            @Nullable Class<?> targetClass,
                                                                            List<Object> interceptorsAndDynamicMethodMatchers) {
        try {
            Constructor<?>[] constructors = ReflectiveMethodInvocation.class.getDeclaredConstructors();
            Constructor<?> constructor = constructors[0];
            constructor.setAccessible(true);
            return (ReflectiveMethodInvocation) constructor.newInstance(proxy, target, method, arguments, targetClass,
                    interceptorsAndDynamicMethodMatchers);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 自定义调用链条 -- 责任链设计模式
    @Test
    public void testMyInvocation() throws Throwable {
        Target target = new Target();
        List<MethodInterceptor> list = new ArrayList<>();
        list.add(new MyAdvice(1));
        list.add(new MyAdvice(2));
        // 添加自定义
        MyInvocation myInvocation = new MyInvocation(target, Target.class.getMethod("foo"), new Object[0], list);
        myInvocation.proceed();

    }

    @Order(1) // Order只能作用类, 不能用于方法, 如果需要对方法的通知指定排序, 可以针对每个方法单独创建一个类
    static class Aspect {

        @Before("execution(* foo())")
        public void before() {
            System.out.println("before");
        }

        @After("execution(* foo())")
        public void after() {
            System.out.println("after");
        }

        @AfterReturning("execution(* foo())")
        public void afterReturning() {
            System.out.println("afterReturning");
        }

        @AfterThrowing("execution(* foo())")
        public void afterThrowing() {
            System.out.println("afterThrowing");
        }

        @Around("execution(* foo())")
        public void around() {
            System.out.println("around");
        }

    }

    static class Target {
        public void foo() {
            System.out.println("foo");
        }
    }

    // 自定义MethodInterceptor
    static class MyAdvice implements MethodInterceptor {

        private final int no;

        public MyAdvice(int no) {
            this.no = no;
        }

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            System.out.println("MyAdvice before" + no);
            Object result = invocation.proceed();
            System.out.println("MyAdvice after" + no);
            return result;
        }
    }

    static class MyInvocation implements MethodInvocation {

        private final Object target;
        private final Method method;
        private final Object[] args;
        List<MethodInterceptor> methodInterceptors;
        private int index = 0;

        public MyInvocation(Object target, Method method, Object[] args, List<MethodInterceptor> methodInterceptors) {
            this.target = target;
            this.method = method;
            this.args = args;
            this.methodInterceptors = methodInterceptors;
        }

        @Override
        public Method getMethod() {
            return method;
        }

        @Override
        public Object[] getArguments() {
            return args;
        }

        @Override
        public Object proceed() throws Throwable {
            if (index >= methodInterceptors.size()) {
                return method.invoke(target, args);
            }
            MethodInterceptor methodInterceptor = methodInterceptors.get(index++);
            return methodInterceptor.invoke(this);
        }

        @Override
        public Object getThis() {
            return target;
        }

        @Override
        public AccessibleObject getStaticPart() {
            return method;
        }
    }
}
