package com.arlley.design.mode.spring.core.general;

import com.arlley.design.mode.spring.core.api.BeanDefinition;
import com.arlley.design.mode.spring.core.api.BeanDefinitionRegister;
import com.arlley.design.mode.spring.core.api.BeanFactory;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认的Bean工厂。
 */
public class DefaultBeanFactory implements BeanFactory, BeanDefinitionRegister {

    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

    private Map<String, Object> instanceMap = new ConcurrentHashMap<>(256);

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        Objects.requireNonNull(beanName);
        Objects.requireNonNull(beanDefinition);
        // 不符合bean定义的
        if(!beanDefinition.validate()) {

            return;
        }
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        return null;
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return false;
    }

    @Override
    public Object getBean(String beanName) {
        return null;
    }
}
