package com.wayne.jmx.demo.pmclient;

import com.wayne.jmx.demo.IocContainer;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Histogram;
import io.prometheus.client.Summary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*
        http://localhost:8080/test/a1
http://localhost:8080/actuator/prometheus
http://localhost:9090/
collect:395, Summary (io.prometheus.client)
collect:46, Collector (io.prometheus.client)
findNextElement:197, CollectorRegistry$MetricFamilySamplesEnumeration (io.prometheus.client)
<init>:162, CollectorRegistry$MetricFamilySamplesEnumeration (io.prometheus.client)
<init>:183, CollectorRegistry$MetricFamilySamplesEnumeration (io.prometheus.client)
metricFamilySamples:129, CollectorRegistry (io.prometheus.client)
handle:100, HTTPServer$HTTPMetricHandler (io.prometheus.client.exporter) this.registry.metricFamilySamples()
 */
//复杂类型Summary和Histogram  ---- 在业务代码中进行监控埋点
@Component
public class PrometheusMetricsInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private CollectorRegistry collectorRegistry;

    static final Histogram requestLatencyHistogram = Histogram.build().labelNames("path", "method", "code")
            .name("io_namespace_http_requests_latency_seconds_histogram").help("Request latency in seconds.")
            .register();
    static final Summary requestLatency = Summary.build()
            .name("io_namespace_http_requests_latency_seconds_summary")
            .quantile(0.5, 1) //quantile=0.5 百分之50的操作耗时，中分位数
            .quantile(0.9, 0.01) //9 分位数
            .labelNames("path", "method", "code")
            .help("Request latency in seconds.").register();

    private Histogram.Timer histogramRequestTimer;
    private Summary.Timer summaryRequestTimer;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        int status = response.getStatus();
        histogramRequestTimer = requestLatencyHistogram.labels(requestURI, method, String.valueOf(status)).startTimer();

        summaryRequestTimer = requestLatency.labels(requestURI, method, String.valueOf(status)).startTimer();
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        histogramRequestTimer.observeDuration();

        summaryRequestTimer.observeDuration();
    }

}
