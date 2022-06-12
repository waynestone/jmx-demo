package com.wayne.jmx.demo.spring.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

/*
创建邮件服务，积分服务，其他服务(事件订阅者)等:
 */
@Service
public class EmailService implements ApplicationListener<UserRegisterEvent> {
    @Override
    public void onApplicationEvent(UserRegisterEvent userRegisterEvent) {
        System.out.println("邮件服务接到通知，给 " + userRegisterEvent.getSource() + " 发送邮件...");
    }
}
