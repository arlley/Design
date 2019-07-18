package com.arlley.design.mode.spring.core.general;

import com.arlley.design.mode.spring.core.api.BeanDefinition;
import org.apache.commons.lang3.StringUtils;

public class DefaultBeanDefinition implements BeanDefinition {

    private Class<?> beanClass;

    private String staticConstructorMethod;

    private String constructorMethod;

    private String beanFactoryBeanName;

    private String scorp;

    /**
     * 直接通过beanClass实例化
     */
    DefaultBeanDefinition(Class<?> beanClass, String scorp){
        this.beanClass = beanClass;
        this.scorp = scorp;
    }

    DefaultBeanDefinition(Class<?> beanClass, String staticConstructorMethod, String scorp){
        this.beanClass = beanClass;
        this.staticConstructorMethod = staticConstructorMethod;
        this.scorp = scorp;
    }

    DefaultBeanDefinition(String constructorMethod, String beanFactoryBeanName){
        this.constructorMethod = constructorMethod;
        this.beanFactoryBeanName = beanFactoryBeanName;
    }

    DefaultBeanDefinition(Class<?> beanClass, String staticConstructorMethod,
                          String constructorMethod, String beanFactoryBeanName, String  scorp){
        this.beanClass = beanClass;
        this.staticConstructorMethod = staticConstructorMethod;
        this.constructorMethod = constructorMethod;
        this.beanFactoryBeanName = beanFactoryBeanName;
        this.scorp = scorp;
    }

    @Override
    public Class<?> getBeanClass() {
        return this.beanClass;
    }

    @Override
    public String getStaticConstructorMethod() {
        return this.staticConstructorMethod;
    }

    @Override
    public String getConstructorMethod() {
        return this.constructorMethod;
    }

    @Override
    public String getBeanFactoryBeanName() {
        return this.beanFactoryBeanName;
    }

    @Override
    public String getScorp() {
        return this.scorp;
    }

    @Override
    public boolean isSingleton() {
        return StringUtils.equals(this.scorp, SCORE_SINGLETON);
    }

    @Override
    public boolean isPrototype() {
        return StringUtils.equals(this.scorp, SCORE_PROTOTYPE);
    }
}
