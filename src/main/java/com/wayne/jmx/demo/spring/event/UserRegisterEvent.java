package com.wayne.jmx.demo.spring.event;

import org.springframework.context.ApplicationEvent;

/*
定义一个用户注册事件:
 */
public class UserRegisterEvent extends ApplicationEvent {
    public UserRegisterEvent(String name) { //name即source
        super(name);
    }
}
