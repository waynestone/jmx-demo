package com.wayne.jmx.demo.actuator;

import io.micrometer.core.instrument.Tag;
import org.springframework.boot.actuate.metrics.web.servlet.WebMvcTagsProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*
https://github.com/spring-projects/spring-boot/blob/main/spring-boot-project/spring-boot-actuator/src/main/java/org/springframework/boot/actuate/metrics/web/servlet/WebMvcMetricsFilter.java

WebMvcMetricsFilter.doFilterInternal

记录registry
  start  timingContext = this.startAndAttachTimingContext(request);

  end   this.record(timingContext, request, response, exception);
          timerSample.stop(this.getTimer(builder, handler, request, response, exception));
            private Timer getTimer(Builder builder, Object handler, HttpServletRequest request, HttpServletResponse response, Throwable exception) {
                return builder.tags(this.tagsProvider.getTags(request, response, handler, exception)).register(this.registry);
            }

# HELP http_server_requests_seconds
# TYPE http_server_requests_seconds histogram
 */
public class CustomWebMvcTagsProvider implements WebMvcTagsProvider {

    @Override
    public Iterable<Tag> getTags(HttpServletRequest request, HttpServletResponse response, Object handler, Throwable exception) {
        return null;
    }

    @Override
    public Iterable<Tag> getLongRequestTags(HttpServletRequest request, Object handler) {
        return null;
    }
}
