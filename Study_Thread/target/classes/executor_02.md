## 线程池
## 通过 ThreadPoolExecutor创建线程池
```
 public ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue,
                              ThreadFactory threadFactory,
                              RejectedExecutionHandler defaultHandler
) 
```
## 参数介绍
1. corePoolSize
   - 线程池的基本大小;如果要执行的任务数小于线程池基本大小,提交一个任务到线程池后,就会创建一个线程即便有空闲线程；否则，不创建,等待。
2. maximumPoolSize
   - 线程池最大线程数。如果线程池中的线程数大于核心线程数并且队列满了，且线程数小于最大线程数，则会创建新的线程。
   - 如果maximumPoolSize与corePoolSize相等,即是固定大小线程池。
3. keepAliveTime
   - 空闲线程存活时间。
   - 默认情况下,当线程池中的线程数大于corePoolSize时,keepAliveTime才会起作用;当线程池中的线程空闲时,如果空闲时间等于keepAliveTime,
     线程会被销毁,直到线程数等于核心线程数,避免浪费内存和句柄资源。
   - 当ThreadPoolExecutor的allowCoreThreadTimeOut变量设置为true时，核心线程超时后也会被回收。
4. unit
   - 时间单位
5. BlockingQueue<Runnable> workQueue    
   - 任务队列,用于保存等待执行的任务的阻塞队列。包括以下几种：
   1. ArrayBlockingQueue：一个基于数组结构的有界阻塞队列,FIFO(先进先出)原则对元素进行排序；
   2. LinkedBlockingQueue：一个基于链表结构的阻塞队列,也是先进先出,一般情况下吞吐量高于ArrayBlockingQueue 。 FixedThreadPool()和SingleThreadExecutor() 使用此队列。 
   3. SynchronousQueue：一个不存储元素的阻塞队列。上一个线程执行移除操作后,之后的插入操作才能执行,否则会一直处于阻塞状态。吞吐量高于LinkedBlockingQueue。 CachedThreadPool()使用此队列。
   4. PriorityBlockingQueue： 一个具有优先级的无限阻塞队列。
6. ThreadFactory threadFactory
   - 线程池创建线程使用的工厂；
   - 使用线程池创建线程的时候可以给予更有意义的名称，便于定位问题。
7. RejectedExecutionHandler defaultHandler 
   - 线程池对拒绝任务的处理策略；发生在队列和线程池都满了的时候。默认是AbortPolicy,表示无法处理新任务时抛出异常。
   1. AbortPolicy ： 直接抛出异常；
   2. CallerRunsPolicy: 只用调用者所在线程来运行任务；
   3. DiscardOldestPolicy：丢弃队列里最近的一个任务,并执行当前任务；
   4. DiscardPolicy：不处理，不丢弃；
   - 自定义拒绝策略实现RejectedExecutionHandler接口,实现需要的场景。比如持久化拒绝的任务、记录日志等等。
 
## 线程池的实现原理分析
### 流程图
 ![image](https://yqfile.alicdn.com/d175db1560075ea1540d01676b166699244d9764.png)
### 流程分析
   1. 任务到线程池后,有创建线程执行任务、写入队列、任务拒绝策略三种。新任务,当然不想让它直接执行任务拒绝策略。所以一般来说就是两种情况。
      创建新的线程,耗费系统资源,而且可能会涉及到加锁。写入队列的话相对来说会更好。
   2. 执行任务
      - execute 源码解读
   ```
     public void execute(Runnable command) {
         if (command == null)
             throw new NullPointerException();
         //ctl用来控制线程池的状态，并用来表示线程池线程数量。
         int c = ctl.get();
         //如果当前线程数小于核心线程数
         if (workerCountOf(c) < corePoolSize) {
              // 创建工作线程,添加线程任务
             if (addWorker(command, true))
                 return;
              //获取最新的ct1
             c = ctl.get();
         }
         //如果线程池的状态为Runnable,向工作队列中写入任务
         if (isRunning(c) && workQueue.offer(command)) {
             // 获取ctl
             int recheck = ctl.get();
             // 如果线程不处于Runnable,移出任务
             if (! isRunning(recheck) && remove(command))
                // 执行任务拒绝策略
                 reject(command);
             else if (workerCountOf(recheck) == 0)
                // 如果工作线程为0,创建新的工作线程
                 addWorker(null, false);
         }
         // 非核心线程，执行任务
         else if (!addWorker(command, false))
         // 说明线程池达到饱和，或者线程池shut down，执行拒绝策略
             reject(command);
     }
   ```
   - addWorker
### execute()实现任务的接口

