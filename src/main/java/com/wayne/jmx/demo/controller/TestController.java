package com.wayne.jmx.demo.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

/**
 * @Auther: Wayne
 * @Date: 2021/12/30 10:28
 * @Description:
 */
@RestController
@RequestMapping("/test")
public class TestController {

//    @Resource
//    private PrometheusCustomMonitor monitor;

    @RequestMapping(value = "/a1", method = RequestMethod.GET)
    public String a1() {
//// 统计下单次数
//        monitor.getOrderCount().increment();
//        Random random = new Random();
//        int amount = random.nextInt(100);
//        // 统计金额
//        monitor.getAmountSum().record(amount);

        try{
            Random random=new Random();
            Thread.sleep(random.nextInt(100));
        }catch (Exception ex){

        }

        return "a1,ok, " + System.currentTimeMillis();
    }

    @RequestMapping(value = "/a2", method = RequestMethod.GET)
    public String a2() throws InterruptedException {
        Thread.sleep(500);
        return "a2,ok, " + System.currentTimeMillis();
    }

    @RequestMapping(value = "/a3", method = RequestMethod.GET)
    public String a3() throws InterruptedException {
        Thread.sleep(3000);
        return "a3,ok," + System.currentTimeMillis();
    }

    @RequestMapping(value = "/a5/{num}", method = RequestMethod.GET)
    public String a5(@PathVariable("num") String num) {
        Integer.valueOf(num);
        return "a5,ok:" + num;
    }
}
