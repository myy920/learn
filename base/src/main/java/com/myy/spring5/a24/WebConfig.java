package com.myy.spring5.a24;

import com.myy.spring5.a23.MyDateFormatter;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * @InitBinder的来源
 * 1. @ControllerAdvice中@InitBinder标注的方法, 由RequestMappingHandlerAdapter在初始化时解析并记录
 * 2. @Controller中的@InitBinder标注的方法, 由RequestMappingHandlerAdapter在控制器方法首次执行时解析并记录
 */
@Configuration
public class WebConfig {

    @ControllerAdvice
    static class MyControllerAdvice {

        @InitBinder
        public void binder(WebDataBinder webDataBinder) {
            webDataBinder.addCustomFormatter(new MyDateFormatter("MyControllerAdvice 调用"));
        }

    }

    @Controller
    static class Controller1 {

        @InitBinder
        public void binder(WebDataBinder webDataBinder) {
            webDataBinder.addCustomFormatter(new MyDateFormatter("Controller1 调用"));
        }

        public void foo() {

        }


    }

    @Controller
    static class Controller2 {

        @InitBinder
        public void binder(WebDataBinder webDataBinder) {
            webDataBinder.addCustomFormatter(new MyDateFormatter("Controller2 调用"));
        }

        public void foo() {

        }

    }

}
