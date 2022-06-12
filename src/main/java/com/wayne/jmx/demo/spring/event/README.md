## Spring 中的事件机制 ApplicationEventPublisher
> https://blog.csdn.net/ssssny/article/details/104640105

在设计模式中，观察者模式可以算得上是一个非常经典的行为型设计模式，猫叫了，主人醒了，老鼠跑了，这一经典的例子，是事件驱动模型在设计层面的体现。

另一模式，发布订阅模式往往被人们等同于观察者模式，但我的理解是两者唯一区别，是发布订阅模式需要有一个调度中心，而观察者模式不需要，例如观察者的列表可以直接由被观察者维护。不过两者即使被混用，互相替代，通常不影响表达。
java 和 spring 中都拥有 Event 的抽象，分别代表了语言级别和三方框架级别对事件的支持。

Spring 的文档对 Event 的支持翻译之后描述如下：
> ApplicationContext 通过 ApplicationEvent 类和 ApplicationListener 接口进行事件处理。 如果将实现 ApplicationListener 接口的 bean 注入到上下文中，则每次使用 ApplicationContext 发布 ApplicationEvent 时，都会通知该 bean。本质上，这是标准的观察者设计模式。

通过 demo，看一个电商系统中对 ApplicationEventPublisher 的使用。

我们的系统要求，当用户注册后，给他发送一封邮件通知他注册成功了。

**定义一个用户注册事件:**
```java
public class UserRegisterEvent extends ApplicationEvent{
    public UserRegisterEvent(String name) { //name即source
        super(name);
    }
}
```
ApplicationEvent 是由 Spring 提供的所有 Event 类的基类，为了简单起见，注册事件只传递了 name（可以复杂的对象，但注意要了解清楚序列化机制）。
