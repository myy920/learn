package com.myy.spring.a20;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Collections;

@Configuration
@ComponentScan
@PropertySource(value = "classpath:application.properties")
@EnableConfigurationProperties(value = {WebMvcProperties.class, ServerProperties.class})
public class WebConfig {

    // 內嵌web容器工厂
    @Bean
    public TomcatServletWebServerFactory tomcatServletWebServerFactory(ServerProperties serverProperties) {
        return new TomcatServletWebServerFactory(serverProperties.getPort());
    }

    // 创建DispatcherServlet
    // DispatcherServlet 初始化方法 onRefresh()
    @Bean
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }

    // 注册DispatcherServlet, Spring MVC的入口
    @Bean
    public DispatcherServletRegistrationBean dispatcherServletRegistrationBean(
            DispatcherServlet dispatcherServlet, WebMvcProperties webMvcProperties) {
        DispatcherServletRegistrationBean registrationBean =
                new DispatcherServletRegistrationBean(dispatcherServlet, "/");
        WebMvcProperties.Servlet servlet = webMvcProperties.getServlet();
        registrationBean.setLoadOnStartup(servlet.getLoadOnStartup()); // 启动优先级, 默认-1不启动
        return registrationBean;
    }

    // 避免走默认配置, 加入RequestMappingHandlerMapping
    @Bean
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        return new RequestMappingHandlerMapping();
    }

    // 避免走默认配置, 加入RequestMappingHandlerAdapter
    @Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
        MyRequestMappingHandlerAdapter handlerAdapter = new MyRequestMappingHandlerAdapter();
        // 添加@token注解的参数解析器
        TokenArgumentResolver tokenArgumentResolver = new TokenArgumentResolver();
        handlerAdapter.setCustomArgumentResolvers(Collections.singletonList(tokenArgumentResolver));
        // 添加@Yml注解的返回值处理器
        YmlReturnValueHandler ymlReturnValueHandler = new YmlReturnValueHandler();
        handlerAdapter.setCustomReturnValueHandlers(Collections.singletonList(ymlReturnValueHandler));
        return handlerAdapter;
    }

}
