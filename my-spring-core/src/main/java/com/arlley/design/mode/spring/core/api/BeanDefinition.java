package com.arlley.design.mode.spring.core.api;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Objects;

public interface BeanDefinition {

    final String SCORE_SINGLETON = "singleton";

    final String SCORE_PROTOTYPE = "prototype";

    Class<?> getBeanClass();

    String getStaticConstructorMethod();

    String getConstructorMethod();

    String getBeanFactoryBeanName();

    String getScorp();

    boolean isSingleton();

    boolean isPrototype();

    //DI部分
    List<?> getConstructorArgumentValues();

    Constructor<?> getConstructor();

    void setConstructor(Constructor<?> constructor);

    default boolean validate(){
        if(Objects.isNull(this.getBeanClass())){
            if(StringUtils.isBlank(this.getBeanFactoryBeanName()) || StringUtils.isBlank(this.getConstructorMethod())){
                return false;
            }
        }else{
            if(StringUtils.isNotBlank(this.getBeanFactoryBeanName()) || StringUtils.isNotBlank(this.getBeanFactoryBeanName())){
                return false;
            }
        }
        return true;
    }
}
