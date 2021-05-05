package com.study.rabbitmq.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/4/26 14:53
 */
public class DateUtil {




    public static long getDateDuration(LocalDate start ,LocalDate end){
        return start.until(end, ChronoUnit.DAYS);
    }
}
