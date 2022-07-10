package com.wayne.jmx.demo;

import com.wayne.jmx.demo.mbean.m.PersonAgent;
import com.wayne.jmx.demo.mbean.modelm.ModelMBeanUtils;
import io.micrometer.core.instrument.Metrics;
import io.prometheus.client.CollectorRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.management.*;
import javax.management.modelmbean.RequiredModelMBean;
import java.lang.management.ManagementFactory;

/**
 * 源自： https://cloud.tencent.com/developer/article/1460134
 */
@SpringBootApplication
public class JmxDemoApplication {

    public static void main(String[] args) throws Exception{
        SpringApplication.run(JmxDemoApplication.class, args);

        PersonAgent.registerMBean();



        //create mbean server
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();

        //create object name
        ObjectName helloName = new ObjectName("jmxModelMBean:name=hello");

        //receive ModelMBean by ModelMBeanUtils
        RequiredModelMBean hello = ModelMBeanUtils.createModelMBean();

        //create mbean and register mbean
        server.registerMBean(hello, helloName);

        System.out.println(" modelMBean start.....");

    }

//    @Bean
//    public CollectorRegistry collectorRegistry(){
//        return CollectorRegistry.defaultRegistry;
//    }

}
