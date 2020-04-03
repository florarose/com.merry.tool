package com.java.thread.executor;

import sun.plugin2.jvm.RemoteJVMLauncher;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

/**
 *  自带的线程池
 * @author 坎布里奇
 * @version 1.0
 * @date 2019/11/11 11:34
 */
public class ExecutorDemo {

    public void executorFixedThreadPool(){
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Set<Callable<String>> callables = new HashSet<Callable<String>>();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("Asynchronous task");
            }
        });
//        executorService.shutdown();
//        //runnable
//        executorService.submit(()->{
//
//        });
//        callables.add(() -> {
//            return null;
//        });
        try {
            List<Future<String>> futures = executorService.invokeAll(callables);
            for(Future<String> future : futures){
                System.out.println("future.get = " + future.get());
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }catch (ExecutionException e){
            e.printStackTrace();
        }
        try {
            String getValueA = executorService.invokeAny(callables);
            System.out.println("future.get = " + getValueA);
        }catch (InterruptedException e){
            e.printStackTrace();
        }catch (ExecutionException e){
            e.printStackTrace();
        }
        executorService.shutdown();
    }
    public void executorSingleThreadExecutor(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("Asynchronous task");
            }
        });
        //callable
        Future future =  executorService.submit(() -> {
            return null;
        });
        executorService.shutdown();
    }
    public void executorScheduledThreadPool(){
        ExecutorService executorService = Executors.newScheduledThreadPool(10);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("Asynchronous task");
            }
        });
        executorService.shutdown();
    }
    public static void main(String[] args) {

    }
}
