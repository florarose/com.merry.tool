# 线程池
## jdk 自带的线程池，Executors
## 包含三种创建的方法
  
```
ExecutorService executorService1 = Executors.newSingleThreadExecutor();
ExecutorService executorService2 = Executors.newFixedThreadPool(10);
ExecutorService executorService3 = Executors.newScheduledThreadPool(10);
```

## 执行
### 一、 通过execute
```
    executorService.execute(new Runnable() {
        @Override
        public void run() {
            System.out.println("我要开始运行了");
        }
    });
```
### 二、 通过submit
#### 1.通过提交Runnable
```
    Future future =  executorService.submit(()->{
    });
```
#### 2.通过提交Callable
```
    Future future =  executorService.submit(() -> {
        return null;
    });
```
###￥
### 三、两种执行方法的区别
