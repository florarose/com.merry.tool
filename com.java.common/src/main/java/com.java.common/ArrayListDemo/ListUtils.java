package com.java.common.ArrayListDemo;


import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ListUtils {

    /**
     *  集合
     *  根据对象的日期排序
     * @param list
     */

    public static void ListSort(List<ViewDemo> list) {
        Collections.sort(list, new Comparator<ViewDemo>() {
            @Override
            public int compare(ViewDemo o1, ViewDemo o2) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date dt1 = o1.getCreateTime();
                    Date dt2 = o2.getCreateTime();
                    if (dt2.getTime() > dt1.getTime()) {
                        return 1;
                    } else if (dt2.getTime() < dt1.getTime()) {
                        return -1;
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }

}
