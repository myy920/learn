package com.myy.spring5.a21;

import com.myy.spring5.a20.Person;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
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
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

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
        // 准备ModeAndViewContainer 用来存储中间Model结果
        ModelAndViewContainer container = new ModelAndViewContainer();
        // 解析每个参数值
        System.out.println("-----打印参数信息");
        for (MethodParameter parameter : handlerMethod.getMethodParameters()) {
            //
            RequestParamMethodArgumentResolver resolver =
                    new RequestParamMethodArgumentResolver(null, false);
            String annotations = Arrays.stream(parameter.getParameterAnnotations()).
                    map(parameterAnnotation -> '@' + parameterAnnotation.annotationType().getSimpleName()).collect(Collectors.joining(","));
            // 发现参数名称
            parameter.initParameterNameDiscovery(new DefaultParameterNameDiscoverer());
            //
            Object value = resolver.resolveArgument(parameter, container, new ServletWebRequest(request), null);


            System.out.println(parameter.getParameterIndex() + ":   "
                    + annotations + "   "
                    + parameter.getParameterType().getSimpleName() + "   "
                    + parameter.getParameterName() + "-->"
                    + value);
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
                @RequestParam("file") MultipartFile file,
                @PathVariable("id") int id,
                @RequestHeader("Content-Type") String header,
                @CookieValue("token") String token,
                @Value("${JAVA_HOME}") String home2,
                HttpServletRequest request,
                @ModelAttribute Person person1,
                Person person2,
                @RequestBody Person person3) {

        }

    }

    static class WebConfig {

    }

}
