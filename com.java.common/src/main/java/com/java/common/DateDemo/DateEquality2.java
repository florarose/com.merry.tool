package com.java.common.DateDemo;



//import org.joda.time.LocalDate;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class DateEquality2 {


    /**
     * jdk 1.8 的新特性
     * 比较两个日期是否相等 方法 二
     * @param d1
     * @param d2
     * @return
     */
    private static boolean sameDate(Date d1, Date d2) {
        LocalDate localDate1 = ZonedDateTime.ofInstant(d1.toInstant(), ZoneId.systemDefault()).toLocalDate();
        LocalDate localDate2 = ZonedDateTime.ofInstant(d2.toInstant(), ZoneId.systemDefault()).toLocalDate();
        return localDate1.isEqual(localDate2);
    }
}
