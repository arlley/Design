package com.arlley.design.mode.spring.core.general;

import com.arlley.design.mode.spring.core.api.BeanDefinition;
import com.arlley.design.mode.spring.core.api.BeanDefinitionRegister;
import com.arlley.design.mode.spring.core.api.BeanFactory;
import com.sun.istack.internal.Nullable;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认的Bean工厂。
 */
@Slf4j
public class DefaultBeanFactory implements BeanFactory, BeanDefinitionRegister {

    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

    private Map<String, Object> instanceMap = new ConcurrentHashMap<>(256);

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        long currTime = System.nanoTime();
        log.info("注册{}beanDefinition开始");
        Objects.requireNonNull(beanName);
        Objects.requireNonNull(beanDefinition);
        // 不符合bean定义的
        if(!beanDefinition.validate()) {
            log.info("注册bean：[{}]的beanDefinition 校验不通过！", beanName);
            return;
        }
        if(!this.containsBeanDefinition(beanName)){
            this.beanDefinitionMap.put(beanName, beanDefinition);
        }
        log.info("注册{}beanDefinition结束，共花费{}ns",beanName, System.nanoTime() - currTime);
    }

    @Override
    public @Nullable BeanDefinition getBeanDefinition(String beanName) {
        Objects.requireNonNull(beanName);
        if(!this.beanDefinitionMap.containsKey(beanName)){
            return null;
        }
        return this.beanDefinitionMap.get(beanName);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return this.beanDefinitionMap.containsKey(beanName);
    }

    @Override
    public Object getBean(@NonNull String beanName){
        if(StringUtils.isBlank(beanName)){
            return null;
        }

        Object instance = instanceMap.get(beanName);
        if(!Objects.isNull(instance)){
            return instance;
        }

        //判断初始化方式
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if(Objects.isNull(beanDefinition)){
            log.info("您要获取的bean[{}]未定义", beanName);
            return null;
        }

        if (!Objects.isNull(beanDefinition.getBeanClass())){
            if(StringUtils.isBlank(beanDefinition.getStaticConstructorMethod())){
                // 通过类反射
                try {
                    instance = beanDefinition.getBeanClass().newInstance();
                }catch (InstantiationException e){
                    log.error("bean[{}]创建实例发生异常", beanName, ExceptionUtils.getStackTrace(e));
                }catch (IllegalAccessException e){
                    log.error("bean[{}]无权限访问类", beanName, ExceptionUtils.getStackTrace(e));
                }
            }else{
                //静态方法
                Method method = null;
                try {
                    method = beanDefinition.getBeanClass().getMethod(beanDefinition.getStaticConstructorMethod(), null);
                    instance = method.invoke(null, null);
                }catch (NoSuchMethodException e){
                    log.error("bean[{}]没有对应的静态工厂方法", beanName, ExceptionUtils.getStackTrace(e));
                }catch (IllegalAccessException e){
                    log.error("bean[{}]无权限访问类", beanName, ExceptionUtils.getStackTrace(e));
                }catch (InvocationTargetException e){
                    log.error("bean[{}]执行静态构造方法发生异常", beanName,ExceptionUtils.getStackTrace(e));
                }
            }
        }else{
            //工厂方法构造
            Object beanFactory = this.getBean(beanDefinition.getBeanFactoryBeanName());
            if(!Objects.isNull(beanFactory)){
                Method constructorMethod = null;
                try {
                    constructorMethod = beanFactory.getClass().getMethod(beanDefinition.getConstructorMethod(), null);
                    instance = constructorMethod.invoke(beanFactory, null);
                }catch (NoSuchMethodException e){
                    log.error("工厂方法创建bean[{}]无效", beanName, ExceptionUtils.getStackTrace(e));
                }catch (IllegalAccessException e){
                    log.error("bean[{}]无权限访问类", beanName, ExceptionUtils.getStackTrace(e));
                }catch (InvocationTargetException e){
                    log.error("bean[{}]执行工厂构造方法发生异常", beanName,ExceptionUtils.getStackTrace(e));
                }
            }
        }
        if(Objects.isNull(instance)){
            return null;
        }
        if (beanDefinition.isSingleton()){
            instanceMap.put(beanName, instance);
        }
        return instance;
    }
}
