package com.wayne.jmx.demo.mbean.m;

public class Person implements PersonMBean{
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String sayHello(String hello) {
        System.out.println(hello);
        return this.name + ":" + hello;
    }



}
