package com.java.thread.executor;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2019/11/13 15:34
 */
public interface Notify {

    /**
     * 回调
     */
    void notifyListen() ;

    final void runWorker(ThreadPoolExecutor.Worker w) {
        Thread wt = Thread.currentThread();
        Runnable task = w.firstTask;
        w.firstTask = null;    w.unlock();
        boolean completedAbruptly = true;
        try {
            // task一开始是firstTask， 后面就通过getTask()从阻塞队列里拿任务
            while(task != null || (task = this.getTask()) != null) {
                w.lock();
                if ((runStateAtLeast(this.ctl.get(), 536870912) || Thread.interrupted() && runStateAtLeast(this.ctl.get(), 536870912)) && !wt.isInterrupted()) {
                    wt.interrupt();
                }
                try {
                    this.beforeExecute(wt, task);    // 这个方法子类可以重写，在任务执行前有回调
                    try {
                        task.run(); // 执行任务
                        this.afterExecute(task, (Throwable)null);
                    } catch (Throwable var14) {
                        // 这个方法子类可以重写，在任务执行后有回调
                        this.afterExecute(task, var14);
                        throw var14;
                    }
                } finally {
                    task = null;
                    ++w.completedTasks;
                    w.unlock();
                }
            }
            completedAbruptly = false;
        } finally {
            // 线程池已没有任务了，工作线程达到了可退出的状态，Worker退出
            this.processWorkerExit(w, completedAbruptly);
        }
    }






}
