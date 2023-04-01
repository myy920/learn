package com.myy.spring.a05.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

public class MapperPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
//        try {
//            PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
//            Resource[] resources = patternResolver.getResources("classpath:com/myy/spring/a05/mapper/**/*.class");
//            CachingMetadataReaderFactory readerFactory = new CachingMetadataReaderFactory();
//            for (Resource resource : resources) {
//                MetadataReader metadataReader = readerFactory.getMetadataReader(resource);
//                ClassMetadata classMetadata = metadataReader.getClassMetadata();
//                if (classMetadata.isInterface()) {
//                    AbstractBeanDefinition beanDefinition =
//                            BeanDefinitionBuilder.genericBeanDefinition(MapperFactoryBean.class)
//                                    .addConstructorArgValue(classMetadata.getClassName())
//                                    .setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE)
//                                    .getBeanDefinition();
//                    beanDefinitionRegistry.registerBeanDefinition(classMetadata.getClassName(), beanDefinition);
//                }
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }
}
