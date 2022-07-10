package com.wayne.jmx.demo;


import com.sun.net.httpserver.HttpServer;
import com.wayne.jmx.demo.mbean.m.PersonAgent;
import com.wayne.jmx.demo.mbean.mx.test.DataIntegrationVolume;
import com.wayne.jmx.demo.pmclient.CustomCollector;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.HTTPServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.management.*;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.net.InetSocketAddress;

@Component
@Order(value = 0)
public class CheckMXBeanRunner implements ApplicationRunner {

    @Autowired
    private CollectorRegistry collectorRegistry;

    @Autowired
    PrometheusMeterRegistry prometheusRegistry;


    @Override
    public void run(ApplicationArguments args) throws Exception {

        if(IocContainer.containsEndPoint("prometheusEndpoint")){
            CollectorRegistry registry=IocContainer.getBean(CollectorRegistry.class);

        }
        //

        new CustomCollector().register(collectorRegistry);

        //-------simpleClient
        try {
            //TODO 指标采集端口号
            HTTPServer server1 = new HTTPServer.Builder()
                    .withPort(9090)
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        //-------micrometer
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(9091), 0);
            server.createContext("/prometheus", httpExchange -> {
                String response = prometheusRegistry.scrape();
                httpExchange.sendResponseHeaders(200, response.getBytes().length);
                try (OutputStream os = httpExchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            });

            new Thread(server::start).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
