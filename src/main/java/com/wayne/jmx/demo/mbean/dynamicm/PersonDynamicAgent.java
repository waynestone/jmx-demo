package com.wayne.jmx.demo.mbean.dynamicm;

import com.wayne.jmx.demo.mbean.m.Person;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

public class PersonDynamicAgent {
    public static void main(String[] args) throws Exception {
        // 获取MBeanServer对象
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        ObjectName personName = new ObjectName("jmxBean:name=xiaoming");
        server.registerMBean(new PersonDynamic(new Person("xiaoming", 27)), personName); // 注册MBean
        System.out.println("PersonDynamic monitor....");
        Thread.sleep(60 * 60 * 1000);
    }


}
