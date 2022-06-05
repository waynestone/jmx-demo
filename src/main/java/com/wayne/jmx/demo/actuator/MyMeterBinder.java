package com.wayne.jmx.demo.actuator;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Collections;

@Component
public class MyMeterBinder implements MeterBinder {
    private final Iterable<Tag> tags;

    public MyMeterBinder() {
        this(Collections.emptyList());
    }

    public MyMeterBinder(Iterable<Tag> tags) {
        this.tags = tags;
    }
    @Override
    public void bindTo(MeterRegistry registry) {
        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
        Gauge.builder("jvm.threads.peak", threadBean, ThreadMXBean::getPeakThreadCount).tags(this.tags).description("The peak live thread count since the Java virtual machine started or peak was reset").baseUnit("threads").register(registry);


        Timer timer = Timer.builder("myservice").tag("method", "manual").register(registry);

    }
}
