package com.wayne.jmx.demo.spring.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventDemoApi {

    @Autowired
    UserService userService;

    @RequestMapping("/register")
    public String register(){
        userService.register("wayne.com");
        return "success";
    }

}
