package com.wayne.jmx.demo.mbean.m;

public class Paxi implements PaxiMBean {
    @Override
    public void sayHi() {
        System.out.println("hi");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name=name;
    }
    private String name="paxi";
}
