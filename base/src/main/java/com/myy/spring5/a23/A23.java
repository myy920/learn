package com.myy.spring5.a23;

import com.myy.spring5.Util.List;
import lombok.Data;
import lombok.ToString;
import org.junit.Test;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.DirectFieldAccessor;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestParameterPropertyValues;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.InvocableHandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ServletRequestDataBinderFactory;

import java.util.Date;

public class A23 {


    @Test
    public void simpleConverter() {
        SimpleTypeConverter typeConverter = new SimpleTypeConverter();
        Integer integer = typeConverter.convertIfNecessary("66", int.class);
        System.out.println(integer);
        Date date = typeConverter.convertIfNecessary("1995/08/10", Date.class);
        System.out.println(date);
    }

    @Test
    public void beanWrapper() {
        Bean2 bean2 = new Bean2();
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(bean2);
        beanWrapper.setPropertyValue("a", "10");
        beanWrapper.setPropertyValue("b", "JoJo");
        beanWrapper.setPropertyValue("c", "1995/08/10");
        System.out.println(bean2);
    }

    @Test
    public void filedAccessor() {
        Bean1 bean1 = new Bean1();
        DirectFieldAccessor accessor = new DirectFieldAccessor(bean1);
        accessor.setPropertyValue("a", "10");
        accessor.setPropertyValue("b", "JoJo");
        accessor.setPropertyValue("c", "1995/08/10");
        System.out.println(bean1);
    }

    @Test
    public void dataBinder() {
        Bean2 bean2 = new Bean2();
        DataBinder binder = new DataBinder(bean2);
        binder.initDirectFieldAccess(); // 默认是走set方法设置属性, 加了这个后走field
        MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues.add("a", "10");
        propertyValues.add("b", "JoJo");
        propertyValues.add("c", "1995/08/10");
        binder.bind(propertyValues);
        System.out.println(bean2);
    }

    // web环境参数绑定
    @Test
    public void servletRequestDataBinder() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("a", "10");
        request.setParameter("b", "JoJo");
        request.setParameter("c", "1995/08/10");
        Bean2 bean2 = new Bean2();
        ServletRequestDataBinder dataBinder = new ServletRequestDataBinder(bean2);
        dataBinder.bind(new ServletRequestParameterPropertyValues(request));
        System.out.println(bean2);
    }

    @Test
    public void initBinder() throws Exception {
        MockHttpServletRequest request = newRequest();

        Person person = new Person();
        // 1.@InitBinder
        InvocableHandlerMethod method = new InvocableHandlerMethod(new MyController(), MyController.class.getMethod(
                "test", WebDataBinder.class));
        ServletRequestDataBinderFactory binderFactory = new ServletRequestDataBinderFactory(List.of(method), null);

        WebDataBinder dataBinder = binderFactory.createBinder(new ServletWebRequest(request), person, "person");
        dataBinder.bind(new ServletRequestParameterPropertyValues(request));
        System.out.println(person);

    }

    @Test
    public void conversionService() throws Exception {
        MockHttpServletRequest request = newRequest();
        Person person = new Person();

        FormattingConversionService conversionService = new FormattingConversionService();
        conversionService.addFormatter(new MyDateFormatter());
        ConfigurableWebBindingInitializer initializer = new ConfigurableWebBindingInitializer();
        initializer.setConversionService(conversionService);
        ServletRequestDataBinderFactory binderFactory = new ServletRequestDataBinderFactory(null,
                initializer);

        WebDataBinder dataBinder = binderFactory.createBinder(new ServletWebRequest(request), person, "person");
        dataBinder.bind(new ServletRequestParameterPropertyValues(request));
        System.out.println(person);
    }

    

    private MockHttpServletRequest newRequest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("birthday", "1995|08|10");
        request.setParameter("name", "东莞");
        return request;
    }

    @ToString
    static class Bean1 {

        private int a;

        private String b;

        private Date c;

    }

    @Data
    @ToString
    static class Bean2 {

        private int a;

        private String b;

        private Date c;

    }

    @Data
    static class Person {

        private Date birthday;

        private String name;
    }

    static class MyController {

        @InitBinder
        public void test(WebDataBinder dataBinder) {
            dataBinder.addCustomFormatter(new MyDateFormatter());

        }

    }

}
