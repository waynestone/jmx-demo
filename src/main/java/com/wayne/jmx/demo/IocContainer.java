package com.wayne.jmx.demo;

import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * Created by wayne on 2022/05/11.
 */
public class IocContainer implements ApplicationListener<ApplicationContextEvent> {
    private static ApplicationContext context;

    public static <BeanType> BeanType getBean(Class<BeanType> beanTypeClass) {
        return context.getBean(beanTypeClass);
    }

    public static Object getBean(String beanName) {
        return context.getBean(beanName);
    }

    public static Boolean containsBean(String beanName){
        return context.containsBean(beanName);
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> clazz) {
        return context.getBeansOfType(clazz);
    }

    public static Boolean containsEndPoint(String endPointName) {
        Class annotationType=null;
        try {
            annotationType = Class.forName("org.springframework.boot.actuate.endpoint.annotation.Endpoint");
        }
        catch (ClassNotFoundException ex){

        }
        if(annotationType==null){
            return false;
        }
        String[] beanNames = BeanFactoryUtils.beanNamesForAnnotationIncludingAncestors(context,
                annotationType);
        for (String beanName : beanNames){
            if (beanName.equalsIgnoreCase(endPointName)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void onApplicationEvent(ApplicationContextEvent event) {
        context = event.getApplicationContext();
    }
}
