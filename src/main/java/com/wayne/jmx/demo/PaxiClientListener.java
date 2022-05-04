package com.wayne.jmx.demo;

import javax.management.AttributeChangeNotification;
import javax.management.Notification;
import javax.management.NotificationListener;

public class PaxiClientListener implements NotificationListener {

    @Override
    public void handleNotification(Notification notification, Object handback) {
        System.out.println("r notification");
        System.out.println("class:"+notification.getClass().getName());
        System.out.println("Source:"+notification.getSource());
        System.out.println("Type:"+notification.getType());
        System.out.println("Message:"+notification.getMessage());
        if (notification instanceof AttributeChangeNotification){
            AttributeChangeNotification n= (AttributeChangeNotification) notification;
            System.out.println("attr name:"+n.getAttributeName());
            System.out.println("attr type:"+n.getAttributeType());
            System.out.println("attr new Value:"+n.getNewValue());
            System.out.println("attr old Value:"+n.getOldValue());
        }
    }
}
