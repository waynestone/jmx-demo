package com.wayne.jmx.demo;

import com.wayne.jmx.demo.mbean.m.PaxiMBean;

import javax.management.*;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

public class PaxiClient {
    public static void main(String[] args) {
        System.out.println("create RMI client");
        try {
            JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://:8080/jmxrmi");
            JMXConnector jmxConnector = JMXConnectorFactory.connect(url, null);
            MBeanServerConnection mBeanServerConnection = jmxConnector.getMBeanServerConnection();
            System.out.println("domains");
            String[] domains= mBeanServerConnection.getDomains();
            Arrays.sort(domains);
            for (String domain:domains){
                System.out.println(domain);
            }
            System.out.println("domain:"+mBeanServerConnection.getDefaultDomain());
            System.out.println("MBean count:"+mBeanServerConnection.getMBeanCount());
            Set<ObjectName> names = new TreeSet<ObjectName>(mBeanServerConnection.queryNames(null,null));
            for (ObjectName name:names){
                System.out.println("objectname:"+name);
            }
            ObjectName mbeanName = new ObjectName("main.jmx:type=Paxi");
            PaxiMBean mbeanProxy = JMX.newMBeanProxy(mBeanServerConnection,mbeanName,PaxiMBean.class,true);
            System.out.println("add notification listener..");
            // 自定义消息的监听
            PaxiClientListener listener = new PaxiClientListener();
            mBeanServerConnection.addNotificationListener(mbeanName,listener,null,null);
            mbeanProxy.setName("new name");
            System.out.println("wait notifacaion");
            TimeUnit.SECONDS.sleep(2);
            System.out.println(mbeanProxy.getName());
            mbeanProxy.sayHi();
            TimeUnit.SECONDS.sleep(10);
            jmxConnector.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
