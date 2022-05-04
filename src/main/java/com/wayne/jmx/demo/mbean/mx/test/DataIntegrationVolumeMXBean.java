package com.wayne.jmx.demo.mbean.mx.test;

public interface DataIntegrationVolumeMXBean {
    public String getName();
    public String getType();
    public void init();

    public Book getUsage();

    long getTotal();

    long testTotal(String status);

}
