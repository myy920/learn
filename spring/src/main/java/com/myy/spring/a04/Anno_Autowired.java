package com.myy.spring.a04;

import com.myy.spring.common.Do;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;

public class Anno_Autowired {

    public static void main(String[] args) throws Throwable {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerSingleton("bean2", new Bean2());
        beanFactory.registerSingleton("bean3", new Bean3());

        beanFactory.setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());
        AutowiredAnnotationBeanPostProcessor processor = new AutowiredAnnotationBeanPostProcessor();
        processor.setBeanFactory(beanFactory);

        Bean1 bean1 = new Bean1();
        System.out.println(bean1);

        // processor.postProcessProperties(null, bean1, "bean1"); 方法展开
        InjectionMetadata metadata = (InjectionMetadata) Do.method(processor, "findAutowiringMetadata", String.class,
                Class.class, PropertyValues.class).invoke("bean1", bean1.getClass(), null);
        metadata.inject(bean1, "bean1", null);

        System.out.println(bean1);
    }

}
