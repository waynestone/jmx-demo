package com.wayne.jmx.demo.pmclient;

import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import io.prometheus.client.Summary;

/*
在业务代码中进行监控埋点
//简单类型Gauge和Counter
 */
public class YourClass {
    static final Counter requests = Counter.build()
            .name("requests_total").help("Total requests.").register();

   public static void processRequest() {
        requests.inc();
        // Your code here.
    }


    static final Gauge inprogressRequests = Gauge.build()
            .name("inprogress_requests").help("Inprogress requests.").register();

    void processRequest2() {
        inprogressRequests.inc();
        // Your code here.
        inprogressRequests.dec();
    }



/*
除了使用Timer进行计时以外，Summary实例也提供了timer()方法，可以对线程或者Lamda表达式运行时间进行统计：
 */
    static final Summary requestLatency = Summary.build()
            .name("requests_latency_seconds").help("Request latency in seconds.").register();

    void processRequest3() {
        requestLatency.time(new Runnable() {
            public void run() {
                // Your code here.
                int a=0;
            }
        });

        // Or the Java 8 lambda equivalent
        requestLatency.time(() -> {
            // Your code here.
        });
    }
}
