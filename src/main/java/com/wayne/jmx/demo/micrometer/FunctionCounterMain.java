package com.wayne.jmx.demo.micrometer;

import io.micrometer.core.instrument.FunctionCounter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.search.Search;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.ToDoubleFunction;

/*
FunctionCounter是Counter的特化类型，它把计数器数值增加的动作抽象成接口类型ToDoubleFunction，
这个接口JDK1.8中对于Function的特化类型接口。FunctionCounter的使用场景和Counter是一致的，这里介绍一下它的用法：


FunctionCounter使用的一个明显的好处是，我们不需要感知FunctionCounter实例的存在，
实际上我们只需要操作作为FunctionCounter实例构建元素之一的AtomicInteger实例即可，这种接口的设计方式在很多主流框架里面可以看到。
 */
public class FunctionCounterMain {

    public static void main(String[] args) throws Exception {
        MeterRegistry registry = new SimpleMeterRegistry();
        AtomicInteger n = new AtomicInteger(0);
        //这里ToDoubleFunction匿名实现其实可以使用Lambda表达式简化为AtomicInteger::get
        FunctionCounter.builder("functionCounter", n, new ToDoubleFunction<AtomicInteger>() {
            @Override
            public double applyAsDouble(AtomicInteger value) {
                return value.get();
            }
        }).baseUnit("function")
                .description("functionCounter")
                .tag("createOrder", "CHANNEL-A")
                .register(registry);
        //下面模拟三次计数
        n.incrementAndGet();
        n.incrementAndGet();
        n.incrementAndGet();


        Search.in(registry).meters().forEach(each -> {
            StringBuilder builder = new StringBuilder();
            builder.append("name:")
                    .append(each.getId().getName())
                    .append(",tags:")
                    .append(each.getId().getTags())
                    .append(",type:").append(each.getId().getType())
                    .append(",value:").append(each.measure());
            System.out.println(builder.toString());
        });

    }
}

