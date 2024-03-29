package com.arlley.design.mode.spring.core.api;

import java.lang.reflect.Constructor;

/**
 *<p>
 *     bean的工厂接口。
 *</p>
 * @author wanggang
 * @since 1.0
 */
public interface BeanFactory {

    /**
     * 获取bean的实例。
     * @param beanName
     *        bean的名称。
     * @return
     *        bean的实例。
     */
    Object getBean(String beanName);

    Object[] getConstructorValues(BeanDefinition bd);

    Constructor<?> determineConstructor(BeanDefinition bd);
}
