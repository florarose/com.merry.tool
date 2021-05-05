package com.redis.study.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @auther: 坎布里奇
 * @description: xxxxx
 * @date: 2021/4/15 11:33
 * @version: 1.0.0
 */
@Service
public class LuaService {

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 秒杀功能，调用lua脚本
     *
     * @param actId     活动id
     * @param userId    用户id
     * @param buyNum    购买数量
     * @param skuId     skuid
     * @param perSkuLim 每个用户购买当前sku的数量限制
     * @param perActLim 每个用户购买当前活动内所有sku的数量限制
     * @return
     */
    public String skuSecond(String actId, String userId, int buyNum, String skuId, int perSkuLim, int perActLim) {

        //时间字串，用来区分秒杀成功的订单
        int START = 100000;
        int END = 900000;
        int rand_num = ThreadLocalRandom.current().nextInt(END - START + 1) + START;
        String order_time = getTime(rand_num);

        List<String> keyList = new ArrayList<>();
        keyList.add(userId);
        keyList.add(String.valueOf(buyNum));
        keyList.add(skuId);
        keyList.add(String.valueOf(perSkuLim));
        keyList.add(actId);
        keyList.add(String.valueOf(perActLim));
        keyList.add(order_time);

        String result = redisUtils.runLuaScript("order.lua", keyList);
        System.out.println("------------------lua result:" + result);
        return result;
    }

    private String getTime(int rand_num) {
        Date d = new Date();
        System.out.println(d);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = sdf.format(d);
        return dateNowStr + "-" + rand_num;
    }

}
