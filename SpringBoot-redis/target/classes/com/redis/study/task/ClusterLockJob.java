package com.redis.study.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;

/**
 * @auther: 坎布里奇
 * @description: xxxxx
 * @date: 2021/4/19 17:34
 * @version: 1.0.0
 */
@Component
public class ClusterLockJob {


    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${server.port}")
    private String port;

    public static final String LOCK_PRE = "lock_prefix_";

    @Scheduled(cron = "0/5 * * * * *")
    public void lock(){
        String lockName = LOCK_PRE + "ClusterLockJob";
        String currentValue = getHostIp() + ":" + port;
        Boolean ifAbsent = false;
        try {
            //设置锁
            ifAbsent = redisTemplate.opsForValue().setIfAbsent(lockName, currentValue,60, TimeUnit.SECONDS);
//            ifAbsent = redisTemplate.opsForValue().setIfAbsent(lockName, currentValue);

            if(ifAbsent){
                //获取锁成功，设置失效时间
//                redisTemplate.expire(lockName,60, TimeUnit.SECONDS);
                System.out.println("Lock success,execute business,current time:" + System.currentTimeMillis());
                Thread.sleep(3000);
            }else{
                //获取锁失败
                String value = (String) redisTemplate.opsForValue().get(lockName);
                System.out.println("Lock fail,current lock belong to:" + value);
            }
        }catch (Exception e){
            System.out.println("ClusterLockJob exception:" + e);
        }finally {
            if(ifAbsent){
                //若分布式锁Value与本机Value一致，则当前机器获得锁，进行解锁
                redisTemplate.delete(lockName);
            }
        }
    }

    /**
     * 获取本机内网IP地址方法
     * @return
     */
    private static String getHostIp(){
        try{
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            while (allNetInterfaces.hasMoreElements()){
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()){
                    InetAddress ip = (InetAddress) addresses.nextElement();
                    if (ip != null
                            && ip instanceof Inet4Address
                            && !ip.isLoopbackAddress() //loopback地址即本机地址，IPv4的loopback范围是127.0.0.0 ~ 127.255.255.255
                            && ip.getHostAddress().indexOf(":")==-1){
                        return ip.getHostAddress();
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
