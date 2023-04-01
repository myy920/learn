package com.myy.spring.a13;

import org.springframework.cglib.core.Signature;

public abstract class FastClass {

    public abstract int getIndex(Signature signature);

    public abstract Object invoke(int index, Object target, Object[] args);

}
