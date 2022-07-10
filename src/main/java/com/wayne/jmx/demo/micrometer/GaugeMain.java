package com.wayne.jmx.demo.micrometer;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.TimeGauge;
import io.micrometer.core.instrument.search.Search;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class GaugeMain {
    private static final MeterRegistry registry = new SimpleMeterRegistry();
    private static final BlockingQueue<String> QUEUE = new ArrayBlockingQueue<>(500);
    private static BlockingQueue<String> REAL_QUEUE;

//    static {
//        REAL_QUEUE = registry.gauge("messageGauge", QUEUE, Collection::size);
//    }

    public static void main(){
        registry.gauge("messageGauge", QUEUE, Collection::size);


        AtomicInteger n = registry.gauge("numberGauge", new AtomicInteger(0));
        n.set(1);
        n.set(2);
    }


    /*
    TimeGauge是Gauge的特化类型，相比Gauge，它的构建器中多了一个TimeUnit类型的参数，用于指定ToDoubleFunction入参的基础时间单位。这里简单举个使用例子：
     */
    public static void main(String[] args) throws Exception {
        AtomicInteger count = new AtomicInteger();
        TimeGauge.Builder<AtomicInteger> timeGauge = TimeGauge.builder("timeGauge", count,
                TimeUnit.SECONDS, AtomicInteger::get);
        timeGauge.register(registry);
        count.addAndGet(10086);
        print();
        count.set(1);
        print();
    }

    private static void print() throws Exception {
        Search.in(registry).meters().forEach(each -> {
            StringBuilder builder = new StringBuilder();
            builder.append("name:")
                    .append(each.getId().getName())
                    .append(",tags:")
                    .append(each.getId().getTags())
                    .append(",type:").append(each.getId().getType())
                    .append(",value:").append(each.measure());
            System.out.println(builder.toString());
        });
    }


}
