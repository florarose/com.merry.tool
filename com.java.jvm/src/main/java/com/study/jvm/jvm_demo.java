package com.study.jvm;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 长时间周期:
 * @auther: 坎布里奇
 * @description: xxxxx
 * @date: 2021/3/2 15:27
 * @version: 1.0.0
 */
public class jvm_demo {


    /**
     * 信用卡信息
     */
    private static class CardInfo{

        BigDecimal bigDecimal = new BigDecimal(0.0);
        String name = "张三";
        int age = 5;
        Date birthday = new Date();
        public void m(){

        }
    }

    private static ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(50,new ThreadPoolExecutor.DiscardOldestPolicy());

    public static void main(String[] args) throws Exception {
        executor.setMaximumPoolSize(50);
        for(;;){
            modelFit();
            Thread.sleep(100);
        }
    }

    private static void modelFit(){
        List<CardInfo> cardInfoList = getCardInfo();
        cardInfoList.forEach(info -> {
            executor.scheduleWithFixedDelay(() ->{
                info.m();
            },2,3, TimeUnit.SECONDS);
        });
    }

    private static List<CardInfo> getCardInfo(){
        List<CardInfo> cardInfos = new ArrayList<CardInfo>();
        for (int i = 0; i < 100; i++) {
            CardInfo  cardInfo = new CardInfo();
            cardInfos.add(cardInfo);
        }
        return cardInfos;
    }

}

/**
 * 金融
 *  一个线程池，从数据库中取数据，套用风险模型，算一个风险值，根据风险值来计算他的信用额度是多少 ？
 */

/**
 *  定位问题:
 *  1、资源占用  top  /
 *  2、java 进程     jps 列出所有java进程
 *  3、jstack 1780  列出该进程下所有线程
 */
