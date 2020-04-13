package vedio;

import cn.ucloud.ufile.api.ApiError;
import cn.ucloud.ufile.api.object.PutFileApi;
import cn.ucloud.ufile.bean.PutObjectResultBean;
import cn.ucloud.ufile.bean.UfileErrorBean;
import cn.ucloud.ufile.http.UfileCallback;
import cn.ucloud.ufile.util.JLog;
import com.alibaba.fastjson.JSONObject;
import okhttp3.Request;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/3/27 15:31
 */
public class QuickEmailToWikiExtractor<E> {

    private int queueSize = 5;
    private int producerNum = 2;
    private int consumerNum = 2;
    //创建一个阻塞队列
    private LinkedBlockingQueue<E> blockingQueue = null;
    //生产者线程池
    private ExecutorService producerTheadPool = null;
    //消费者线程池
    private ExecutorService consumerTheadPool = null;

    private Set<Callable<String>> callables = new HashSet<Callable<String>>();

    //重写ThredTactory()方法
    ThreadFactory threadFactory = new ThreadFactory() {
        //  int i = 0;  用并发安全的包装类
        AtomicInteger atomicInteger = new AtomicInteger(1);
        @Override
        public Thread newThread(Runnable r) {
            //创建线程 吧任务传进来
            Thread thread = new Thread(r);
            // 给线程起个名字
            thread.setName("MyThread-" + atomicInteger.getAndIncrement());
            return thread;
        }
    };

    public QuickEmailToWikiExtractor(){
        blockingQueue = new LinkedBlockingQueue<E>(queueSize);
        producerTheadPool = new ThreadPoolExecutor(producerNum,producerNum,0L,TimeUnit.MILLISECONDS, (BlockingQueue<Runnable>) blockingQueue,threadFactory);
        consumerTheadPool = new ThreadPoolExecutor(consumerNum,consumerNum,0L,TimeUnit.MILLISECONDS, (BlockingQueue<Runnable>) blockingQueue,threadFactory);
    }

    public QuickEmailToWikiExtractor(int queueSize, int producerNum, int consumerNum){
        blockingQueue = new LinkedBlockingQueue<E>(queueSize);
        producerTheadPool = Executors.newFixedThreadPool(producerNum,threadFactory);
        consumerTheadPool = Executors.newFixedThreadPool(consumerNum,threadFactory);
    }
    public void produceEleAsync(E ele){
        if(!checkSuccess()){
            return;
        }
        Producer<E> producer = new Producer<E>(this.blockingQueue, ele);
        producerTheadPool.execute(producer);
    }
    public void shutProducerThreadPool(){
        producerTheadPool.shutdown();
        if(! producerTheadPool.isTerminated()){
            try {
                if (!producerTheadPool.awaitTermination(1, TimeUnit.SECONDS)) {
                    producerTheadPool.shutdownNow();
                    System.out.println("线程池没有关闭");
                }
            } catch (InterruptedException e) {
                //awaitTermination方法被中断的时候也中止线程池中全部的线程的执行。
                e.printStackTrace();
                producerTheadPool.shutdownNow();
            }
        }
    }
    //执行消费过程
    public void consumeEleAsync() {
        if(!checkSuccess()){
            return;
        }
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while(true){
//                    try {
//                        E ele = blockingQueue.take();//阻塞获取数据
//                        Consumer<E> consumer = new Consumer<E>(ele);
//                        consumerTheadPool.submit(consumer);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
             new Thread(()-> {
                while(true){
                    try {
                        E ele = blockingQueue.take();//阻塞获取数据
                        Consumer<E> consumer = new Consumer<E>(ele);
                       consumerTheadPool.submit(consumer);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
}

    //判空检查
    private boolean checkSuccess(){
        if(blockingQueue!=null
                && producerTheadPool!=null
                && consumerTheadPool!=null){
            return true;
        }
        return false;
    }
    //生产者
    private class Producer<E> implements Runnable{
        private LinkedBlockingQueue<E> blockingQueue;
        private E ele;

        public Producer(LinkedBlockingQueue<E> blockingQueue, E ele){
            this.blockingQueue = blockingQueue;
            this.ele = ele;
        }

        @Override
        public void run() {
            if(this.blockingQueue!=null && ele!=null){
                try {
                    this.blockingQueue.put(ele);
                } catch (InterruptedException e) {
                    e.getStackTrace();
                }
            }
        }
    }
    //消费者
    private class Consumer<E> implements Callable{
        private E ele;

        public Consumer(E ele){
            this.ele = ele;
        }
        @Override
        public Object call() throws Exception {
            //执行消费过程
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(ele!=null){
                PutFileApi p = (PutFileApi)ele;
                p.executeAsync(new UfileCallback<PutObjectResultBean>() {
                    @Override
                    public void onProgress(long bytesWritten, long contentLength) {
                        LinkedBlockingQueue<String> progressQueue = new LinkedBlockingQueue();
                        JLog.D(TAG, String.format("[progress] = %d%% - [%d/%d]", (int) (bytesWritten * 1.f / contentLength * 100), bytesWritten, contentLength));
                        progressQueue.offer((int) (bytesWritten * 1.f / contentLength * 100)+"'");
                    }
                    @Override
                    public void onResponse(PutObjectResultBean response) {
                        JLog.D(TAG,response.getCallbackRet());
                        JLog.D(TAG,response.geteTag());
                        JSONObject jsonObject = (JSONObject) JSONObject.parse(response.toString());
                        if(null != jsonObject){
                            System.out.println("aa:"+jsonObject.get("RetCode"));
                        }
                        JLog.D(TAG, String.format("[res] = %s", (response == null ? "null" : response.toString())));
                    }

                    @Override
                    public void onError(Request request, ApiError error, UfileErrorBean response) {
                        JLog.D(TAG, String.format("[error] = %s\n[info] = %s",
                                (error == null ? "null" : error.toString()),
                                (response == null ? "null" : response.toString())));
                    }
                });
                System.out.println("当前线程--->"+Thread.currentThread().getName());
            }
            return null;
        }
    }
//    //消费者
//    private class Consumer<E> implements Runnable{
//        private E ele;
//
//        public Consumer(E ele){
//            this.ele = ele;
//        }
//
//        @Override
//        public void run() {
//            //执行消费过程
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            if(ele!=null){
//                PutFileApi p = (PutFileApi)ele;
//                p.executeAsync(new UfileCallback<PutObjectResultBean>() {
//                   @Override
//                   public void onProgress(long bytesWritten, long contentLength) {
//
//
//                       LinkedBlockingQueue<String> progressQueue = new LinkedBlockingQueue();
//                       JLog.D(TAG, String.format("[progress] = %d%% - [%d/%d]", (int) (bytesWritten * 1.f / contentLength * 100), bytesWritten, contentLength));
//                       progressQueue.offer((int) (bytesWritten * 1.f / contentLength * 100)+"'");
//                   }
//
//                   @Override
//                   public void onResponse(PutObjectResultBean response) {
//                       JLog.D(TAG,response.getCallbackRet());
//                       JLog.D(TAG,response.geteTag());
//
//                       JSONObject jsonObject = (JSONObject) JSONObject.parse(response.toString());
//                       if(null != jsonObject){
//                           System.out.println("aa:"+jsonObject.get("RetCode"));
//                       }
//
//                       JLog.D(TAG, String.format("[res] = %s", (response == null ? "null" : response.toString())));
//                   }
//
//                   @Override
//                   public void onError(Request request, ApiError error, UfileErrorBean response) {
//                       JLog.D(TAG, String.format("[error] = %s\n[info] = %s",
//                               (error == null ? "null" : error.toString()),
//                               (response == null ? "null" : response.toString())));
//                   }
//               });
//                System.out.println("当前线程--->"+Thread.currentThread().getName());
//            }
//        }
//    }
}
