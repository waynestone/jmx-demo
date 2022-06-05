package com.wayne.jmx.demo.micrometer;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.search.Search;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
「使用场景：」
Counter的作用是记录XXX的总量或者计数值，适用于一些增长类型的统计，例如下单、支付次数、HTTP请求总量记录等等，通过Tag可以区分不同的场景，对于下单，可以使用不同的Tag标记不同的业务来源或者是按日期划分，对于HTTP请求总量记录，可以使用Tag区分不同的URL。用下单业务举个例子：

作者：Throwable
链接：https://juejin.cn/post/6847902218910334984
来源：稀土掘金
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 */
public class CounterMain {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    static {
        Metrics.addRegistry(new SimpleMeterRegistry());
    }

    public static void main(String[] args) throws Exception {
        Order order1 = new Order();
        order1.setOrderId("ORDER_ID_1");
        order1.setAmount(100);
        order1.setChannel("CHANNEL_A");
        order1.setCreateTime(LocalDateTime.now());
        createOrder(order1);
        Order order2 = new Order();
        order2.setOrderId("ORDER_ID_2");
        order2.setAmount(200);
        order2.setChannel("CHANNEL_B");
        order2.setCreateTime(LocalDateTime.now());
        createOrder(order2);
        createOrder(order2);

        Metrics.counter("order.test",
                "channel", "abc").increment();
        Metrics.counter("order.test",
                "channel", "abc").increment();

        Search.in(Metrics.globalRegistry).meters().forEach(each -> {
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

    private static void createOrder(Order order) {
        //忽略订单入库等操作
        Metrics.counter("order.create",
                "channel", order.getChannel(),
                "createTime", FORMATTER.format(order.getCreateTime())).increment();
    }



    //实体
    public static class Order {

        private String orderId;
        private Integer amount;
        private String channel;
        private LocalDateTime createTime;

        public Order() {
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public LocalDateTime getCreateTime() {
            return createTime;
        }

        public void setCreateTime(LocalDateTime createTime) {
            this.createTime = createTime;
        }
    }


    /*
    上面的例子是使用全局静态方法工厂类Metrics去构造Counter实例，
    实际上，io.micrometer.core.instrument.Counter接口提供了一个内部建造器类Counter.Builder去实例化Counter，
    Counter.Builder的使用方式如下：
     */
    public static class CounterBuilderMain {

        public static void main(String[] args) throws Exception{
            Counter counter = Counter.builder("name")  //名称
                    .baseUnit("unit") //基础单位
                    .description("desc") //描述
                    .tag("tagKey", "tagValue")  //标签
                    .register(new SimpleMeterRegistry());//绑定的MeterRegistry
            counter.increment();
            System.out.println(counter.measure());
        }
    }

}

