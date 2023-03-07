package com.myy.spring5.a19;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.ReflectiveMethodInvocation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.lang.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

public class A19 {

    public static void main(String[] args) throws Throwable {
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean(ConfigurationClassPostProcessor.class);
        context.registerBean(Config.class);
        context.refresh();

        AnnotationAwareAspectJAutoProxyCreator creator = context.getBean(AnnotationAwareAspectJAutoProxyCreator.class);
        List<Advisor> advisors = findEligibleAdvisors(creator, Target.class, "target");

        Target target = new Target();
        ProxyFactory factory = new ProxyFactory();
        factory.setTarget(target);
        factory.addAdvisors(advisors);
        Object proxy = factory.getProxy();

        List<Object> interceptors = factory.getInterceptorsAndDynamicInterceptionAdvice(Target.class.getMethod("foo",
                int.class), Target.class);
        interceptors.forEach(System.out::println);

        ReflectiveMethodInvocation invocation = newReflectiveMethodInvocation(proxy, target, Target.class.getMethod(
                "foo",
                int.class), new Object[]{666}, Target.class, interceptors);
        invocation.proceed();

    }

    private static List<Advisor> findEligibleAdvisors(AnnotationAwareAspectJAutoProxyCreator instance,
                                                      Class<?> beanClass,
                                                      String beanName) {
        try {

            Method method = instance.getClass().getSuperclass().getSuperclass().getDeclaredMethod(
                    "findEligibleAdvisors", Class.class, String.class);
            method.setAccessible(true);
            return (List<Advisor>) method.invoke(instance, beanClass, beanName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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

    @Configuration
    static class Config {
        @Bean
        AnnotationAwareAspectJAutoProxyCreator proxyCreator() {
            return new AnnotationAwareAspectJAutoProxyCreator();
        }

        @Bean
        MyAspect myAspect() {
            return new MyAspect();
        }
    }

    @Aspect
    static class MyAspect {

        @Before("execution(* foo(..))") // 静态通知调用, 不带参数, 执行时不需要切点
        public void before1() {
            System.out.println("before1");
        }

        @Before("execution(* foo(..)) && args(x)") // 动态通知调用, 需要参数绑定, 执行时候需要切点对象
        public void before1(int x) {
            System.out.println("before2 args=" + x);
        }

    }

    static class Target {
        public void foo() {
            System.out.println("foo");
        }

        public void foo(int x) {
            System.out.println("foo args=" + x);
        }
    }

}
