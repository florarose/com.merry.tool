package com.java.common.DateDemo;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.util.Date;

public class DateEquality {


    /**
     *
     * 比较两个日期是否相等 方法 一
     * @param dt1
     * @param dt2
     * @return
     */
    public static Boolean sameDate(Date dt1 , Date dt2 )
    {
        LocalDate ld1 = new LocalDate(new DateTime(dt1));
        LocalDate ld2 = new LocalDate(new DateTime(dt2));
        return ld1.equals( ld2 );
    }

}
