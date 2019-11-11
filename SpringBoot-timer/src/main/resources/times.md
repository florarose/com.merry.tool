# 定时任务
## 主要有三种：
 1. Timer 和 ScheduleExecutorService
 2. SpringTask
 3. Quartz
## Time 方式
### 1. 例子
 ```
  public static void main(String[] args) {
        Timer timer = new Timer();
        /**
        *  timerTask：需要执行的任务
        *  delay：延迟时间（以毫秒为单位）
        *  period：间隔时间（以毫秒为单位）
        */
        timer.schedule(()->{
           System.out.println("开始执行任务:" + LocalDateTime.now());
        }, 
        1000, 1000);
    }
 ```
###  2. 定义
  - 只能让程序按照某一个频率执行，不能指定在某一个时间。
  - 运行多个任务时，其中一个报错或者出现死循环，其余任务无法运行或自动终止运行。
  - 单线程，效率低
###  3. 不经常使用，不推荐。
###  4. 核心代码解读
  1. 接口中两个核心字段: TaskQueue和TimerThread。
  2. TaskQueue是一个优先级队列，内部是由动态数组实现的最小堆结构。
  3. TimerThread是Timer的内部类，继承了Thread并重写了run方法，此线程实例在构建Timer实例的时候被启动。run方法内部会循环的从TaskQueue队列   
     中获取任务，如果没有任务就阻塞自己，如果成功了向队列中添加了定时任务，也会尝试唤醒该线程。
  4. schedule（）配置定时任务的方法，包括六个。
##  ScheduleExecutorService
  1. 例子
```
    public static void main(String[] args) {
        // 重点是，此处的线程出，默认只有1个线程，其余任务需要等待该线程完成，可以设置多线程。
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        long startTime = System.currentTimeMillis();
        System.out.println("第一次执行");
        executorService.schedule(()->{
            System.out.println(System.currentTimeMillis() - startTime);
            try {
                // 此处休眠4秒
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 2, TimeUnit.SECONDS);
        System.out.println("第二次执行");
        executorService.schedule(()->{
            System.out.println(System.currentTimeMillis() - startTime);
        }, 2, TimeUnit.SECONDS);
    }
```    
  2. 定义
   - JDK 1.5新增的，不仅支持远程线程池的功能，也支持定时任务处理的功能。
   - ScheduledExecutorService包括三个方法：schedule()、scheduleAtFixedRate()、scheduleWithFixedDelay()
   - 多线程并发处理，互不干扰。    
  3. 实现原理
   - ScheduledExecutorService的实现类是ScheduledThreadPoolExecutor,实现schedule()方法，采用优先队列DelayedWorkQueue，保证队列头的任务
     是剩余时间最少、需要优先执行的。优先队列保证了每次添加任务到队列中，可以按照剩余时间的多少来顺序的存储任务。线程池中的线程，调用getTask()方法
     获取队列头元素时，会把队列头元素中的时间和当前时间对比，判断是否可以达到可以执行的时间。如果时间相等，返回队列头给线程执行，否则不返回。
  
## Spring Task (一般使用)
### 1. 例子    
   > 依赖
  ```
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>
  ```
   > 
### 2. 定义
  - spring 3.0 后新增的task,一个轻量级的Quartz,满足基本功能，使用较为简单。
### 3. 注解
#### 1. @Scheduled
  - cron : cron表达式，根据表达式循环执行，将时间进行了切割。 @Scheduled(cron="0 0 10 * * ?");
  - fixedRate: 隔多长时间执行一次，（@Scheduled(fixedRate=1000)）
  - fixedDelay: 当前任务执行完毕后等待多久继续下次任务（@Scheduled(fixedDelay=1000)）
  - initialDelay: 
#### 2. @Async
  - 定义: 代表该任务可以进行异步工作，由原来的串行改为并行;
  - Async注解提供了异步调用方法的功能，当调用由此注解的方法的时候方法调用者会马上返回而不会等待调用的方法执行完成，被调用的方法会从线程池中分配一个线程来执行此方法。
#### 3. @EnableScheduling
  - 定义: 表示对@Scheduled注解的开启，
#### 4. @EnableAsync
  - 定义: 表示开启@Async注解。作用就是把串行化的任务并行化。
### 4. 实现
  - 基于注解，@Scheduled ，使用简单，但是@Scheduled只支持常量，修改后，需要重启。
  - 基于接口，继承SchedulingConfigurer，重写configureTasks。
## Quartz
