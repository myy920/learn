package com.myy.spring.a13;

import org.springframework.cglib.core.Signature;

public class TargetFastClass extends FastClass{

    @Override
    public int getIndex(Signature signature) {
        return 0;
    }

    @Override
    public Object invoke(int index, Object target, Object[] args) {
        return null;
    }
}
