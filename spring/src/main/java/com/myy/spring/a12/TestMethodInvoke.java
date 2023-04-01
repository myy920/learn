package com.myy.spring.a12;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class TestMethodInvoke {

    private final int num;

    public TestMethodInvoke(int num) {
        this.num = num;
    }

    public static void main(String[] args) throws Exception {
        TestMethodInvoke instance = new TestMethodInvoke(666);
        Method foo = TestMethodInvoke.class.getMethod("foo", int.class);
        for (int i = 0; i < 20; i++) {
            foo.invoke(instance, i);
            show(foo);
        }
        System.in.read();
    }

    private static void show(Method method) {
        try {
            Field methodAccessor = method.getClass().getDeclaredField("methodAccessor");
            methodAccessor.setAccessible(true);
            Object o = methodAccessor.get(method);
            Field delegate = o.getClass().getDeclaredField("delegate");
            delegate.setAccessible(true);
            Object o1 = delegate.get(o);
            System.out.println(o1.getClass());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void foo(int i) {
        System.out.println("instance:" + num + " --> " + "foo: " + i);
    }

}
