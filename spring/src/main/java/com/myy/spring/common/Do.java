package com.myy.spring.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Do {

    private Object target;

    private Method method;

    public static Do method(Object target, String methodName, Class<?>... paramTypes) {
        Do aDo = new Do();
        try {
            Method declaredMethod = target.getClass().getDeclaredMethod(methodName, paramTypes);
            declaredMethod.setAccessible(true);
            aDo.target = target;
            aDo.method = declaredMethod;
            return aDo;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return aDo;
    }

    public Object invoke(Object... args) {
        try {
            return this.method.invoke(target, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return "";
    }

}
