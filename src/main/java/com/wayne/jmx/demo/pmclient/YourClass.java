package com.wayne.jmx.demo.pmclient;

import io.prometheus.client.Counter;

public class YourClass {
    static final Counter requests = Counter.build()
            .name("requests_total").help("Total requests.").register();

   public static void processRequest() {
        requests.inc();
        // Your code here.
    }
}
