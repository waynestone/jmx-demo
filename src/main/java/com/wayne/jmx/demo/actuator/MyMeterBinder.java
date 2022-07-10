package com.wayne.jmx.demo.actuator;

import io.micrometer.core.instrument.*;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.stereotype.Component;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MyMeterBinder implements MeterBinder {
    private final Iterable<Tag> tags;

    public MyMeterBinder() {
        this(Tags.of("name","xiaoming"));
//        this(Collections.emptyList());
    }

    public MyMeterBinder(Iterable<Tag> tags) {
        this.tags = tags;
    }
    @Override
    public void bindTo(MeterRegistry registry) {
//        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
//        Gauge.builder("jvm.threads.peak", threadBean, ThreadMXBean::getPeakThreadCount).tags(this.tags).description("The peak live thread count since the Java virtual machine started or peak was reset").baseUnit("threads").register(registry);

        //test
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

        Gauge.builder("jmx.test.person", mBeanServer, (s) -> {
            return this.safeDouble(() -> {
                return s.getAttribute(new ObjectName("jmxBean:name=xiaoming"), "Age");
            });
        }).tags(tags).baseUnit("xxx").register(registry);


//        Gauge gauge = Gauge
//                .builder("gauge", myObj, myObj::gaugeValue)
//                .description("a description of what this gauge does") // 可选
//                .tags("region", "test") // 可选
//                .register(registry);


        Timer timer = Timer.builder("myservice").tag("method", "manual").register(registry);

    }

    private double safeDouble(Callable<Object> callable) {
        try {
            return Double.parseDouble(callable.call().toString());
        } catch (Exception var3) {
            return 0.0D / 0.0;
        }
    }
}
