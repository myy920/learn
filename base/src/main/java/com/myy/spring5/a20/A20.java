package com.myy.spring5.a20;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class A20 {

    public static final Logger log = LoggerFactory.getLogger(A20.class);

    public static void main(String[] args) throws Exception {
        AnnotationConfigServletWebServerApplicationContext context =
                new AnnotationConfigServletWebServerApplicationContext(WebConfig.class);
        RequestMappingHandlerMapping handlerMapping = context.getBean(RequestMappingHandlerMapping.class);
        MyRequestMappingHandlerAdapter handlerAdapter = context.getBean(MyRequestMappingHandlerAdapter.class);

        System.out.println(">>>>>模拟请求b");
        mockRequestB(handlerMapping, handlerAdapter);

        System.out.println(">>>>>模拟请求c 获取自定义token");
        mockRequestC(handlerMapping, handlerAdapter);

        System.out.println(">>>>>模拟请求d 自定义Yml");
        mockRequestD(handlerMapping, handlerAdapter);

    }


    private static void mockRequestB(RequestMappingHandlerMapping handlerMapping,
                                     MyRequestMappingHandlerAdapter handlerAdapter) throws Exception {
        // 解析RequestMapping 以及派生注解, 生成路径与控制器方法的映射关系, 在初始化时生成
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();
        // 查看映射结果
        handlerMethods.forEach((k, v) -> System.out.println(k + "=" + v));

        // 模拟请求
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/b");
        request.setParameter("name", "张飞");
        MockHttpServletResponse response = new MockHttpServletResponse();
        // 获取控制器方法 返回处理器执行链对象
        HandlerExecutionChain chain = handlerMapping.getHandler(request);
        System.out.println(chain);

        // HandlerAdapter调用控制器方法
        System.out.println("-------------调用b");
        handlerAdapter.invokeHandlerMethod(request, response, (HandlerMethod) chain.getHandler());

        System.out.println("-------------handlerAdapter所有的参数解析器");
        List<HandlerMethodArgumentResolver> argumentResolvers = handlerAdapter.getArgumentResolvers();
        argumentResolvers.forEach(System.out::println);

        System.out.println("-------------handlerAdapter所有的返回值解析器");
        List<HandlerMethodReturnValueHandler> returnValueHandlers = handlerAdapter.getReturnValueHandlers();
        returnValueHandlers.forEach(System.out::println);
    }

    private static void mockRequestC(RequestMappingHandlerMapping handlerMapping,
                                     MyRequestMappingHandlerAdapter handlerAdapter) throws Exception {
        // 发起请求
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/c");
        request.addHeader("token", "某个令牌");
        MockHttpServletResponse response = new MockHttpServletResponse();

        // handlerMapping通过请求获取控制器方法的处理器执行链
        HandlerExecutionChain chain = handlerMapping.getHandler(request);

        handlerAdapter.invokeHandlerMethod(request, response, (HandlerMethod) chain.getHandler());


    }

    private static void mockRequestD(RequestMappingHandlerMapping handlerMapping,
                                     MyRequestMappingHandlerAdapter handlerAdapter) throws Exception {
        // 模拟请求
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/d.yml");
        MockHttpServletResponse response = new MockHttpServletResponse();

        // 调用处理器映射器, 通过请求获取控制器方法的处理器执行链
        HandlerExecutionChain chain = handlerMapping.getHandler(request);

        handlerAdapter.invokeHandlerMethod(request, response, (HandlerMethod) chain.getHandler());

        byte[] content = response.getContentAsByteArray();
        System.out.println(new String(content, StandardCharsets.UTF_8));

    }
}
