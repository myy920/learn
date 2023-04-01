package com.myy.spring.a13;

import org.springframework.cglib.core.ReflectUtils;
import org.springframework.cglib.core.Signature;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class Proxy extends Target {

    private static MethodInterceptor methodInterceptor;
    private static Method foo;
    private static MethodProxy fooMP;
    private static Object[] args;
    private static Method fooI;
    private static MethodProxy fooIMP;
    private static Method fooS;
    private static MethodProxy fooSMP;

    static {
        try {

            args = new Object[0];
            Class<?> clazz = Class.forName("com.myy.spring.a13.Proxy");
            Class<?> clazz2 = Class.forName("com.myy.spring.a13.Target");
            Method[] methodArray = ReflectUtils.findMethods(
                    new String[]{"foo", "()V", "foo", "(I)V", "foo", "(Ljava/lang/String;)V"},
                    clazz2.getDeclaredMethods());
            foo = methodArray[0];
            fooMP = MethodProxy.create(clazz2, clazz, "(Ljava/lang/String;)V", "foo", "CGLIB$foo$0");
            fooI = methodArray[1];
            fooIMP = MethodProxy.create(clazz2, clazz, "(I)V", "foo", "CGLIB$foo$1");
            fooS = methodArray[2];
            fooSMP = MethodProxy.create(clazz2, clazz, "()V", "foo", "CGLIB$foo$2");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static MethodProxy CGLIB$findMethodProxy(Signature signature) {
        String string = ((Object) signature).toString();
        switch (string.hashCode()) {
            case -1829765895: {
                if (!string.equals("foo(Ljava/lang/String;)V")) break;
                return fooMP;
            }
            case -1268936465: {
                if (!string.equals("foo()V")) break;
                return fooSMP;
            }
            case -682295308: {
                if (!string.equals("foo(I)V")) break;
                return fooIMP;
            }
        }
        return null;
    }

    @Override
    public final void foo(int n) {
        try {
            if (methodInterceptor != null) {
                Object object = methodInterceptor.intercept(this, fooI, new Object[]{new Integer(n)},
                        fooIMP);
                return;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        super.foo(n);
    }

    @Override
    public final void foo(String string) {
        try {
            if (methodInterceptor != null) {
                Object object = methodInterceptor.intercept(this, foo, new Object[]{string},
                        fooMP);
                return;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        super.foo(string);
    }

    @Override
    public final void foo() {
        try {
            if (methodInterceptor != null) {
                Object object = methodInterceptor.intercept(this, fooS, args, fooSMP);
                return;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        super.foo();
    }

}






