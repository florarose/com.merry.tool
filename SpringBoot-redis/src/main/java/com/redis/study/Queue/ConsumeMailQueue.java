package com.redis.study.Queue;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * @auther: 坎布里奇
 * @description: xxxxx
 * @date: 2021/4/19 18:42
 * @version: 1.0.0
 */
@Component
@Slf4j
public class ConsumeMailQueue {

    // 线程池维护线程的最少数量
    private final static int CORE_POOL_SIZE = 1;
    // 线程池维护线程的最大数量
    private final static int MAX_POOL_SIZE = 1;
    // 线程池维护线程所允许的空闲时间
    private final static int KEEP_ALIVE_TIME = 0;
    // 线程池所使用的缓冲队列大小
    private final static int WORK_QUEUE_SIZE = 1;

    // 消息缓冲队列
    Queue<String> msgQueue = new LinkedList<String>();


    //由于超出线程范围和队列容量而使执行被阻塞时所使用的处理程序
    final RejectedExecutionHandler handler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            log.info("线程池太忙了处理不了过多任务.........多余的线程将会放入msgQueue");
            //可以新开调度器进行处理这些调度任务，或者把没处理的任务保存到数据库中，然后定时任务继续处理
            msgQueue.add(((PollMail)r).getEmail());


        }
    };

    // 任务线程池
    final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
            CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME,
            TimeUnit.SECONDS, new ArrayBlockingQueue(WORK_QUEUE_SIZE), handler);


    // 调度线程池。此线程池支持定时以及周期性执行任务的需求。
    final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


    @PostConstruct
    public void init() {

        //开启邮件消费队列检查
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true){
                        String s = RedisQUeue.getRedisQueue().consume();
                        log.info("剩余邮件总数:{}",RedisQUeue.getRedisQueue().size());
                        threadPool.execute(new PollMail(mailService,mail));
                    }
                } catch (InterruptedException e) {
                    log.info("邮件队列消费失败，失败原因为---->",e.getMessage());
                }


            }
        }).start();
    }

    @Data
    class PollMail implements Runnable {

        IMailService mailService;

        Email email;

        public PollMail(IMailService mailService,Email email) {
            this.mailService = mailService;
            this.email = email;
        }

        @Override
        public void run() {
            logger.info("正在处理的邮件为----->{}",this.email.getEmail());
            mailService.dealSend(this.email);
        }
    }
}
