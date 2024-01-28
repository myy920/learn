package com.myy;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class NonRepeatInterceptor implements HandlerInterceptor {

    @Resource
    private NonRepeatService nonRepeatService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Method method = ((HandlerMethod) handler).getMethod();
        NonRepeat nonRepeat = AnnotationUtils.getAnnotation(method, NonRepeat.class);
        if (nonRepeat != null) {
            String nonRepeatToken = request.getHeader("nonRepeatToken");
            if (nonRepeatToken == null) {
                throw new RuntimeException("missing nonRepeatToken");
            }
            if(nonRepeatService.deleteToken(nonRepeatToken)){
                return true;
            }else {
                System.out.println("token[" + nonRepeatToken + "] has used" );
                return false;
            }
        }
        return true;
    }

}
