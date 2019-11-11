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
#### Runnable 和 Callable 的区别
1. 非常相似，这两个接口都表示可以由线程或ExecutorService同时执行的任务。
2. 不同之处是两个接口内部执行的方法不同。   
- Runnable
```
    public interface Runnable {
        public void run();
    }
```
- Runnable
```
    public interface Callable{
        public Object call() throws Exception;
    }
```   
- 可以看出,call()方法是有返回值的，而且可以引发异常。run没有返回值，也不能引发异常（除非未经检查的异常-RuntimeException的子类） 
3. 如果,执行的任务需要返回结果，使用Callable。

### 三、两种执行方法的区别
1. execute 只能提交Runnable类型的任务；submit 除了可以提交Runnable类型的任务外，还可以提交Callable类型。
2. execute 直接抛出任务执行的异常，submit 会捕获，可以通过返回值Future的get方法将执行任务时的异常重新抛出。
3. submit 的顶级接口是ExecutorService；execute 的顶级接口是 Executor；
## 获取返回结果
### 一、 两种 invokeAny() 和 invokeAll()
### 二、 invokeAny 
1. 当任意一个任务得到结果后，会调用interrupt方法将其他的任务中断;
2. 部分任务失败，会使用第一个成功的任务返回的结果;
3. 任务全部失败了，抛出Execption,invokeAny 方法将抛出ExecutionException。
### 三、 invokeAll 返回所有任务的执行结果，该方法的执行效果也是阻塞执行的，要把所有的结果都取回时再继续向下执行。

## 关闭 ExectorService 
### shutdown()
1. 停止接收新的任务并且等待已经提交的任务（包含提交正在执行和提交未执行）执行完成。在终止前允许执行以前提交的任务；
### shutdownNow() 
1. 阻止等待任务的启动并试图停止当前正在执行的任务。不允许执行以前提交的任务。
### awaitTermination()
1. 接收timeout和TimeUnit两个参数，用于设定超时时间及单位。当等待超过设定时间时，会监测ExecutorService是否已经关闭，   
    若关闭则返回true，否则返回false。一般情况下会和shutdown方法组合使用。
### 在实际使用过程中, 使用shutdown()关闭，回收资源。如果有必要，可以在其后执行shutdownNow(),取消所有遗留的任务。


## 例子
```
ExecutorService executorService = Executors.newSingleThreadExecutor();
Set<Callable<String>> callables = new HashSet<Callable<String>>();
callables.add(() -> {
        return "Task 1";
    }
});
callables.add(() -> {
        return "Task 2";
    }
});
List<Future<String>> futures = executorService.invokeAll(callables);
for(Future<String> future : futures){
    System.out.println("future.get = " + future.get());
}
executorService.shutdown()
while (!service.awaitTermination(1, TimeUnit.SECONDS)) {
            System.out.println("线程池没有关闭");
      }
```

