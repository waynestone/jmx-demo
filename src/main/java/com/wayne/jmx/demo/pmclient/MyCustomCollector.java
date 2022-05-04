package com.wayne.jmx.demo.pmclient;

import io.prometheus.client.Collector;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.*;

public class MyCustomCollector extends Collector {
    @Override
    public List<MetricFamilySamples> collect() {

        List<MetricFamilySamples> mfs = new ArrayList<MetricFamilySamples>();
        String metricName = "data_integration_volume";
        //MBeanServer ssssss= getMBeanServer();
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

        List<Collector.MetricFamilySamples.Sample> samples=new ArrayList<>();
        try {
            Set<ObjectName> testobjs = mbs.queryNames(new ObjectName("com.jmx.wayne:type=DataIntegrationVolume,*"), null);
            //ObjectName testo = testobjs.iterator().next();

            if (!testobjs.isEmpty()) {
                Iterator var4 = testobjs.iterator();
                while (var4.hasNext()) {
                    ObjectName testo = (ObjectName) var4.next();
                    String name=testo.getKeyProperty("name");
                    //testo._propertyList.get("name")
                    mbs.invoke(testo, "init", null, null);
                    Object v1 = mbs.getAttribute(testo, "Total");
                    Object v2 = mbs.invoke(testo, "testTotal",new Object[]{"running"},new String[]{String.class.getName()});
                    MetricFamilySamples.Sample sample = new MetricFamilySamples.Sample(metricName, Arrays.asList("status", "name"), Arrays.asList("all", name), Double.valueOf(v1.toString()));
                    samples.add(sample);
                    sample = new MetricFamilySamples.Sample(metricName, Arrays.asList("status", "name"), Arrays.asList("running", name), Double.valueOf(v2.toString()));
                    samples.add(sample);

                }
                MetricFamilySamples metricFamilySamples = new MetricFamilySamples(metricName, Type.GAUGE, "help", samples);

                mfs.add(metricFamilySamples);
            }


            return mfs;

        }
        catch (Exception ex){
            ex.printStackTrace();
        }


        return null;
    }

    public static MBeanServer getMBeanServer() {
        List<MBeanServer> mBeanServers = MBeanServerFactory.findMBeanServer((String)null);
        return !mBeanServers.isEmpty() ? (MBeanServer)mBeanServers.get(0) : ManagementFactory.getPlatformMBeanServer();
    }
}
