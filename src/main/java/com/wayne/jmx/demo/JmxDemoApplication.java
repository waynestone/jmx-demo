package com.wayne.jmx.demo;

import com.wayne.jmx.demo.mbean.modelm.ModelMBeanUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sun.management.ManagementFactoryHelper;

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
//        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
//        try {
//            //2:每个MBean必须有一个object name,name遵照标准格式
//            ObjectName name = new ObjectName("main.jmx:type=Paxi");
//            Paxi paxi = new Paxi();
//            //3:注册MBean
//            mbs.registerMBean(paxi,name);
//            System.out.println("wait for incoming request");
//            Thread.sleep(Long.MAX_VALUE);
//        } catch (MalformedObjectNameException e) {
//            e.printStackTrace();
//        } catch (NotCompliantMBeanException e) {
//            e.printStackTrace();
//        } catch (InstanceAlreadyExistsException e) {
//            e.printStackTrace();
//        } catch (MBeanRegistrationException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


//        ManagementFactoryHelper.getMemoryPoolMXBeans()
//        ManagementFactoryHelper.getMemoryMXBean()


        //create mbean server
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();

        //create object name
        ObjectName helloName = new ObjectName("jmxModelMBean:name=hello");

        //receive ModelMBean by ModelMBeanUtils
        RequiredModelMBean hello = ModelMBeanUtils.createModelMBean();

        //create mbean and register mbean
        server.registerMBean(hello, helloName);

        System.out.println(" modelMBean start.....");
        //HtmlAdaptorServer的下载问题
//        //create adaptor, adaptor is just a form as show mbean. It has no relation to specific business mbean.
//        HtmlAdaptorServer adaptor  = new HtmlAdaptorServer();
//        //create adaptor name
//        ObjectName adaptorName = new ObjectName("jmxAaptor:name=adaptor,port=5050");
//        //register adaptor and adaptor name
//        server.registerMBean(adaptor, adaptorName);
//
//        adaptor.setPort(9999);
//        adaptor.start();
//        //http://localhost:5050/
//        System.out.println("....................server start....................");
        //==============================end============

    }

}
