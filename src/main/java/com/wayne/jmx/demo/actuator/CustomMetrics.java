package com.wayne.jmx.demo.actuator;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.stereotype.Component;

@Component
public class CustomMetrics implements MeterRegistryCustomizer<MeterRegistry> {
    @Override
    public void customize(MeterRegistry registry) {
        String host="1";
        String port="2";

        //唯一标识, IP:port
        registry.config()
                .commonTags("ip", host + "" )
                .commonTags("port",  port+"")
                .commonTags("uuid", host + ":" + port);
    }
}
