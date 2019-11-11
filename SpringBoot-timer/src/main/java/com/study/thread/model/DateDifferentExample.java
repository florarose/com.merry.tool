package com.study.thread.model;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2019/11/5 17:54
 */
public class DateDifferentExample {
    public static int getTime(Date dateStarts, Date dateStops){


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String dateStart = format.format(dateStarts);
        String dateStop = format.format(dateStops);
        Date d1 = null;
        Date d2 = null;

        try {
            d1 = format.parse(dateStart);
            d2 = format.parse(dateStop);

            //毫秒ms
            long diff = d2.getTime() - d1.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = (diff / (60 * 1000) % 60)*60;
            long diffHours = ((diff / (60 * 60 * 1000) % 24)*60*60);
            long diffDays = diff / (24 * 60 * 60 * 1000);

            //指定小数点后面的位数（2位）

            System.out.print("两个时间相差：");
            System.out.print( diffHours +diffSeconds+diffMinutes+  " 秒 ");
            System.out.print(diffHours + " 小时, ");
            System.out.print(diffMinutes + " 分钟, ");
            System.out.print(diffSeconds + " 秒.");
            long a = diffHours+diffSeconds+diffMinutes;
            System.out.println(a);
            return Integer.parseInt(a+"");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void main(String[] args) {
        String dateStart = "2013-02-20 09:29:00";
        String dateStop = "2013-02-20 11:31:00";

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date d1 = null;
        Date d2 = null;

        try {
            d1 = format.parse(dateStart);
            d2 = format.parse(dateStop);

            //毫秒ms
            long diff = d2.getTime() - d1.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = (diff / (60 * 1000) % 60)*60;
            long diffHours = ((diff / (60 * 60 * 1000) % 24)*60*60);
            long diffDays = diff / (24 * 60 * 60 * 1000);

            //指定小数点后面的位数（2位）

            System.out.print("两个时间相差：");
            System.out.print( diffHours +diffSeconds+diffMinutes+  " 秒 ");
            System.out.print(diffHours + " 小时, ");
            System.out.print(diffMinutes + " 分钟, ");
            System.out.print(diffSeconds + " 秒.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
