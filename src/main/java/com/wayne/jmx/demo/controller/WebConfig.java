package com.wayne.jmx.demo.controller;

import com.wayne.jmx.demo.pmclient.PrometheusMetricsInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Auther: Wayne
 * @Date: 2022/3/24 17:34
 * @Description:
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private PrometheusMetricsInterceptor prometheusMetricsInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(prometheusMetricsInterceptor).addPathPatterns("/**");
    }
}
