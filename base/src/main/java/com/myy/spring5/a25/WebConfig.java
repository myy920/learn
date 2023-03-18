package com.myy.spring5.a25;

import lombok.Data;
import lombok.ToString;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Configuration
public class WebConfig {

    @ControllerAdvice
    public static class MyControllerAdvice{
        @ModelAttribute("bar")
        public String bar(){
            return "返回 bar";
        }
    }

    @Controller
    public static class Controller1 {

        @ResponseStatus(HttpStatus.OK)
        public ModelAndView foo(@ModelAttribute Person person) {
            System.out.println("foo args->" + person);
            return null;
        }

    }

    @Data
    @ToString
    public static class Person {

        private String name;


    }


}
