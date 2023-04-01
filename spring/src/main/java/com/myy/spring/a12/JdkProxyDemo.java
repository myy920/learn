package com.myy.spring.a12;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;

public class JdkProxyDemo {

    public static void main(String[] args) {
        Target target = new Target();
        Foo targetProxy = new $Proxy((proxy, method, args1) -> {
            System.out.println("before");
            Object res = method.invoke(target, args1);
            System.out.println("after");
            return res;
        });
        targetProxy.foo();
        targetProxy.bar("test");

    }

    interface Foo {
        void foo();

        String bar(String text);
    }

    static class Target implements Foo {
        @Override
        public void foo() {
            System.out.println("foo");
        }

        @Override
        public String bar(String text) {
            System.out.println("bar " + text);
            return text;
        }
    }

    static class $Proxy implements Foo {

        private static final Method foo;
        private static final Method bar;

        static {
            try {
                foo = Target.class.getDeclaredMethod("foo");
                bar = Target.class.getDeclaredMethod("bar", String.class);
            } catch (NoSuchMethodException e) {
                throw new UndeclaredThrowableException(e);
            }
        }

        private final InvocationHandler invocationHandler;

        public $Proxy(InvocationHandler invocationHandler) {
            this.invocationHandler = invocationHandler;
        }

        @Override
        public void foo() {
            invoke(this, foo, new Object[0]);
        }

        @Override
        public String bar(String text) {
            return (String) invoke(this, bar, new Object[]{text});
        }

        private Object invoke($Proxy proxy, Method method, Object[] args) {
            try {
                return invocationHandler.invoke(this, method, args);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
    }


}
