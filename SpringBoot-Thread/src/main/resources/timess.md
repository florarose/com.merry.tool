## ScheduledExecutorService
## spring-boot 定时任务之Scheduled Task

Spring有两个线程池的实现类，分别为：SimpleThreadPoolTaskExecutor和ThreadPoolTaskExecutor，其中当我们有Quarts和非Quarts共享同一个线程池的需求的时候使用SimpleThreadPoolTaskExecutor，除了这种情况，我们一般是使用
ThreadPoolTaskExecutor，此实现可以通过属性注入来配置线程池的相关配置。 ThreadPoolTaskExecutor中属性注入的源码如下：此配置可以在运行期修改，代码中修改过程使用了同步控制。




Spring框架提供了线程池和定时任务执行的抽象接口：TaskExecutor和TaskScheduler来支持异步执行任务和定时执行任务功能。同时使用框架自己定义的抽象接口来屏蔽掉底层JDK版本间以及Java EE中的线程池和定时任务处理的差异。
另外Spring还支持集成JDK内部的定时器Timer和Quartz Scheduler框架。



一个项目中既需要异步任务, 也需要调度任务, 想把这两个异步线程池分来就需要配置两个线程池。
调度任务添加 @Scheduled 注解, 需要异步执行的方法添加 @Async 注解

中间遇到点小问题, 异步任务线程池总是不生效, 而是使用的调度任务线程池, 经过查文档不断尝试解决了.
公司利用 slf4j 的 MDC 做链路跟踪, 所以还需要添加前置操作, 使用 TaskDecorator 实现。