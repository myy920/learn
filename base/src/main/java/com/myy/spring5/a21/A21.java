package com.myy.spring5.a21;

import com.myy.spring5.a20.Person;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockPart;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExpressionValueMethodArgumentResolver;
import org.springframework.web.method.annotation.RequestHeaderMethodArgumentResolver;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolverComposite;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.PathVariableMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;
import org.springframework.web.servlet.mvc.method.annotation.ServletCookieValueMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;
import org.springframework.web.servlet.mvc.method.annotation.ServletRequestDataBinderFactory;
import org.springframework.web.servlet.mvc.method.annotation.ServletRequestMethodArgumentResolver;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 学到了什么
 * a. 参数解析器可以做什么
 *      1) 查看是否支持某种参数
 *      2) 获取参数值
 * b. 参数解析器的组合模式composite
 *
 *
 */
public class A21 {

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(WebConfig.class);
        // 模拟请求
        MockHttpServletRequest request = newMockHttpServletRequest();
        // 控制器方法被封装成HandlerMethod
        HandlerMethod handlerMethod = new HandlerMethod(new Controller(), Controller.class.getMethod("test",
                String.class,
                String.class, int.class,
                String.class, MultipartFile.class, int.class, String.class, String.class, String.class,
                HttpServletRequest.class, Person.class, Person.class, Person.class));
        // 准备对象绑定与类型转换
        ServletRequestDataBinderFactory binderFactory = new ServletRequestDataBinderFactory(null, null);
        // 准备ModeAndViewContainer 用来存储中间Model结果
        ModelAndViewContainer container = new ModelAndViewContainer();
        // 解析每个参数值
        System.out.println("-----打印参数信息");
        for (MethodParameter parameter : handlerMethod.getMethodParameters()) {

            // 多个解析器的组合
            HandlerMethodArgumentResolverComposite composite = new HandlerMethodArgumentResolverComposite();
            composite.addResolvers(new RequestParamMethodArgumentResolver(context.getBeanFactory(), false),
                    new PathVariableMethodArgumentResolver(),
                    new RequestHeaderMethodArgumentResolver(context.getBeanFactory()),
                    new ServletCookieValueMethodArgumentResolver(context.getBeanFactory()),
                    new ExpressionValueMethodArgumentResolver(context.getBeanFactory()),
                    new ServletRequestMethodArgumentResolver(),
                    new ServletModelAttributeMethodProcessor(false),
                    new RequestResponseBodyMethodProcessor(Collections.singletonList(new MappingJackson2HttpMessageConverter())),
                    new ServletModelAttributeMethodProcessor(true),
                    new RequestParamMethodArgumentResolver(context.getBeanFactory(),true));

            String annotations = Arrays.stream(parameter.getParameterAnnotations()).
                    map(parameterAnnotation -> '@' + parameterAnnotation.annotationType().getSimpleName()).collect(Collectors.joining(","));
            // 发现参数名称
            parameter.initParameterNameDiscovery(new DefaultParameterNameDiscoverer());
            if (composite.supportsParameter(parameter)) {
                // 解析参数
                Object value = composite.resolveArgument(parameter, container, new ServletWebRequest(request),
                        binderFactory);
                System.out.println(parameter.getParameterIndex() + ":   "
                        + annotations + "   "
                        + parameter.getParameterType().getSimpleName() + "   "
                        + parameter.getParameterName() + "-->"
                        + value + "(" + (value == null ? null : value.getClass()) + ")"
                        + "\n\t\t\t" + container.getModel());
            }

        }


    }

    private static MockHttpServletRequest newMockHttpServletRequest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("name1", "zhangfei");
        request.setParameter("name2", "guanyu");
        request.addPart(new MockPart("file", "abc", "hello".getBytes(StandardCharsets.UTF_8)));
        Map<String, String> uriTemplateVariables = new AntPathMatcher().extractUriTemplateVariables("/test/{id}",
                "/test/666");
        request.setAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, uriTemplateVariables);
        //request.setContentType("multipart/form-data");
        request.setContentType("application/json");
        request.setCookies(new Cookie("token", "token123"));
        request.setParameter("name", "张飞");
        request.setParameter("age", "16");
        request.setContent("{\"name\":\"关羽\",\"age\":18}".getBytes(StandardCharsets.UTF_8));
        return request;
    }

    static class Controller {

        public void test(
                @RequestParam("name1") String name1,
                String name2,
                @RequestParam("age") int age,
                @RequestParam(name = "home", defaultValue = "${JAVA_HOME}") String home1,
                @RequestParam(value = "file", required = false) MultipartFile file,
                @PathVariable("id") int id,
                @RequestHeader("Content-Type") String header,
                @CookieValue("token") String token,
                @Value("${JAVA_HOME}") String home2,
                HttpServletRequest request,
                @ModelAttribute(name = "userWithName") Person person1,
                Person person2,
                @RequestBody Person person3) {

        }

    }

    static class WebConfig {

    }

}
