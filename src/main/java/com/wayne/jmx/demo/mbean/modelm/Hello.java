package com.wayne.jmx.demo.mbean.modelm;

public class Hello {
    private String name;

    public String getName() {
        return name;
    }

    public void printHello() {
        System.out.println("Hello World Model Bean......" + name);
    }

    public void printHello(String name) {
        System.out.println("Hello World Model Bean......." + name);
    }

    public void setName(String name) {
        this.name = name;
        System.out.println("My value is set to " + name);
    }
}
