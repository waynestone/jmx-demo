package com.wayne.jmx.demo.micrometer;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import io.prometheus.client.CollectorRegistry;

import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args){


        timer1();

        //Counter
//标签值为 china的请求次数
        MeterRegistry meterRegistry = new SimpleMeterRegistry();
//  写法一
        Counter counter = meterRegistry.counter("request.times.order", "country", "China");
        //  写法二
        Counter counter2 = Counter
                .builder("request.times.order")
                .baseUnit("short") // optional
                .description("a description of what this counter does") // optional
                .tags("country", "China") // optional
                .register(meterRegistry);//
//请求次数+1
        counter.increment();
        counter2.increment();
        System.out.println(counter.measure()); // [Measurement{statistic='COUNT', value=1.0}]







    }


    //内存中
    public static void timer1(){
        Timer timer = Timer.builder("timer")
                .tag("timer", "timersample")
                .description("timer sample test.")
                .register(new SimpleMeterRegistry());
        for(int i=0; i<2; i++) {
            timer.record(() -> {
                createOrder();
            });
        }
        System.out.println(timer.count());
        System.out.println(timer.measure());
        System.out.println(timer.totalTime(TimeUnit.SECONDS));
        System.out.println(timer.mean(TimeUnit.SECONDS));
        System.out.println(timer.max(TimeUnit.SECONDS));
    }
    //全局
    public static void timer2(){
        Timer timer = Metrics.timer("timer2", "timer", "timersample");
        for(int i=0; i<2; i++) {
            timer.record(() -> {
                createOrder();
            });
        }
        System.out.println(timer.count());
        System.out.println(timer.measure());
        System.out.println(timer.totalTime(TimeUnit.SECONDS));
        System.out.println(timer.mean(TimeUnit.SECONDS));
        System.out.println(timer.max(TimeUnit.SECONDS));
    }

    private static void createOrder() {
        try {
            TimeUnit.SECONDS.sleep(5); //模拟方法耗时
        } catch (InterruptedException e) {
            //no-operation
        }
    }

}
