package com.wayne.jmx.demo.mbean.mx.test;

import java.beans.ConstructorProperties;

public class DataOrder {
    private String status;
    private String name;

    @ConstructorProperties({ "status", "name"})
    public DataOrder(String status, String name) {
        this.status = status;
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
