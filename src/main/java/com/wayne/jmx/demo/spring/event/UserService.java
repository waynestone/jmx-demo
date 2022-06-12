package com.wayne.jmx.demo.spring.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

/*
再定义一个用户注册服务(事件发布者):

 */
@Service
public class UserService implements ApplicationEventPublisherAware {
    public void register(String name) {
        System.out.println("用户：" + name + " 已注册！");
        applicationEventPublisher.publishEvent(new UserRegisterEvent(name));
    }

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}


/*
这样也是可以的，那问题来了，ApplicationEventPublisherAware意义是什么
>> ApplicationContextAware ???
@Service
public class UserService {
    public void register(String name) {
        System.out.println("用户：" + name + " 已注册！");
        applicationEventPublisher.publishEvent(new UserRegisterEvent(name));
    }

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
}
 */