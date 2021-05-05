package com.redis.study.Queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @auther: 坎布里奇
 * @description: xxxxx
 * @date: 2021/4/19 18:38
 * @version: 1.0.0
 */
public class RedisQUeue {

    //队列大小
    static final int QUEUE_MAX_SIZE   = 1000;

    static BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<String>(QUEUE_MAX_SIZE);

    /**
     * 私有的默认构造子，保证外界无法直接实例化
     */
    private RedisQUeue(){};
    /**
     * 类级的内部类，也就是静态的成员式内部类，该内部类的实例与外部类的实例
     * 没有绑定关系，而且只有被调用到才会装载，从而实现了延迟加载
     */
    private static class SingletonHolder{
        /**
         * 静态初始化器，由JVM来保证线程安全
         */
        private  static RedisQUeue queue = new RedisQUeue();
    }
    //单例队列
    public static RedisQUeue getRedisQueue(){
        return SingletonHolder.queue;
    }
    //生产入队
    public  void  produce(String s) throws InterruptedException {
        blockingQueue.put(s);
    }
    //消费出队
    public  String consume() throws InterruptedException {
        return blockingQueue.take();
    }
    // 获取队列大小
    public int size() {
        return blockingQueue.size();
    }

}
