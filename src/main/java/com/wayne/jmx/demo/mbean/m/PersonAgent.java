package com.wayne.jmx.demo.mbean.m;

import com.wayne.jmx.demo.mbean.dynamicm.PersonDynamic;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

public class PersonAgent {
    public static void main(String[] args) throws Exception {
        registerMBean();
        Thread.sleep(60 * 60 * 1000);
    }

    public static void registerMBean() throws Exception{
        // 获取MBeanServer对象
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        ObjectName personName = new ObjectName("jmxBean:name=xiaoming");
        server.registerMBean(new Person("xiaoming", 27), personName); // 注册MBean
        System.out.println(" Person monitor....");
    }


}
