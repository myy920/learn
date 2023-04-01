package com.myy.spring.a08.scope1;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("singleton")
@Component
public class BeanForSingleton {
}
