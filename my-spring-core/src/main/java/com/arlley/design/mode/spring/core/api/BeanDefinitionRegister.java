package com.arlley.design.mode.spring.core.api;

/**
 * <p>
 *     bean的注册器。
 * </p>
 * @author wanggang
 */
public interface BeanDefinitionRegister {

    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    BeanDefinition getBeanDefinition(String beanName);

    boolean containsBeanDefinition(String beanName);
}
