package com.wayne.jmx.demo.mbean.mx.test;

import java.util.ArrayList;
import java.util.List;

public class DataIntegrationVolume implements DataIntegrationVolumeMXBean{

    private String name;
    private String type;

    public DataIntegrationVolume(String name) {
        this.name = name;
    }

    private List<DataOrder> dataOrders;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void init() {
        type = "HEAP";
        dataOrders = new ArrayList<>();
        dataOrders.add(new DataOrder("running", "name1"));
        dataOrders.add(new DataOrder("running", "name3"));
        dataOrders.add(new DataOrder("stop", "name2"));
    }

    @Override
    public Book getUsage() {
        return null;
    }

    @Override
    public long getTotal() {
        return dataOrders.size();
    }

    @Override
    public long testTotal(String status) {
        String aaaa="";
        return dataOrders.stream().filter(a->a.getStatus().equals(status)).count();
    }
}
