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
 */
@Component
public class PrometheusMetricsInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private CollectorRegistry collectorRegistry;

    static final Histogram requestLatencyHistogram = Histogram.build().labelNames("path", "method", "code")
            .name("io_namespace_http_requests_latency_seconds_histogram").help("Request latency in seconds.")
            .register();
    static final Summary requestLatency = Summary.build()
            .name("io_namespace_http_requests_latency_seconds_summary")
            .quantile(0.5, 1)
            .quantile(0.9, 0.01) //TODO  啥意思？？？？
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
