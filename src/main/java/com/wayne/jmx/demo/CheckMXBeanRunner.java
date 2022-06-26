package com.wayne.jmx.demo;


import com.wayne.jmx.demo.mbean.mx.test.DataIntegrationVolume;
import com.wayne.jmx.demo.pmclient.MyCustomCollector;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.HTTPServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

@Component
@Order(value = 0)
public class CheckMXBeanRunner implements ApplicationRunner {

    @Autowired
    private CollectorRegistry collectorRegistry;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        //1：获取平台已经创建并初始化的MBeanServer,没有就通过MBeanServerFactory.createMBeanServer()创建
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        try {
            ObjectName name = new ObjectName("com.jmx.wayne:type=PaxiQ,name=test1");
//            Queue<String> queue = new ArrayBlockingQueue<String>(10);
//            queue.add("r-1");
//            queue.add("r-2");
//            queue.add("r-3");
//            PaxiQ mxbean = new PaxiQ("test1",queue);
//
//            //3:注册MBean
//            mbs.registerMBean(mxbean,name);
//            System.out.println("wait for incoming request");

            name = new ObjectName("com.jmx.wayne:type=DataIntegrationVolume,name=syw");
            DataIntegrationVolume dataIntegrationVolume=new DataIntegrationVolume("syw");
            mbs.registerMBean(dataIntegrationVolume,name);

            name = new ObjectName("com.jmx.wayne:type=DataIntegrationVolume,name=wxn");
            dataIntegrationVolume=new DataIntegrationVolume("wxn");
            mbs.registerMBean(dataIntegrationVolume,name);


//            Set<ObjectName> testobjs = mbs.queryNames(new ObjectName("com.jmx.wayne:type=DataIntegrationVolume,*"), null);
//            ObjectName testo=testobjs.iterator().next();
//            mbs.invoke(testo,"init",null,null);
//            Object v2= mbs.getAttribute(testo,"Total");

            //Thread.sleep(Long.MAX_VALUE);

            //((CompositeDataSupport) v).contents

//            try {
//                Set<ObjectName> objs = mbs.queryNames(new ObjectName("com.jmx.wayne:type=PaxiQ,*"), null);
//                if (!objs.isEmpty()) {
//                    Iterator var4 = objs.iterator();
//                    while (var4.hasNext()) {
//                        ObjectName o = (ObjectName) var4.next();
//                        Object v = mbs.getAttribute(o, "PaxiQueue");
//                        int a = 0;
//                    }
//                }
//
//
//            }catch (Exception ex){
//
//            }


            //创建一个AdaptorServer，这个类将决定MBean的管理界面，这里用最普通的Html型界面。AdaptorServer其实也是一个MBean。
            // alpha:name=HelloWorld的名字是有一定规则的，格式为：“域名:name=MBean名称”，域名和MBean名称都可以任意取。
//            ObjectName adapterName = new ObjectName("HelloAgent:name=htmladapter,port=8082");
//            HtmlAdaptorServer adapter = new HtmlAdaptorServer();
//            mbs.registerMBean(adapter, adapterName);
//            adapter.start();
//            System.out.println("start.....");


        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
        } catch (NotCompliantMBeanException e) {
            e.printStackTrace();
        } catch (InstanceAlreadyExistsException e) {
            e.printStackTrace();
        } catch (MBeanRegistrationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        if(IocContainer.containsEndPoint("prometheusEndpoint")){
            CollectorRegistry registry=IocContainer.getBean(CollectorRegistry.class);

        }
        //

        new MyCustomCollector().register();

        try {
            //TODO 指标采集端口号
            HTTPServer server1 = new HTTPServer.Builder()
                    .withPort(9090)
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
