package com.merry.mysql.rw.config;

/**
 * @auther: 坎布里奇
 * @Version: 1.0.0V
 * @date: 2020/9/11 14:20
 * @Description: 定义ThreadLocal存储
 */
public class DataSourceContextHolder {

    private static final ThreadLocal<DataSourceTypeEnum> contextHolder = new ThreadLocal<>();

    public static void setDatabaseType(DataSourceTypeEnum databaseType) {
        contextHolder.set(databaseType);
    }

    public static DataSourceTypeEnum getDatabaseType() {
        return contextHolder.get();
    }

}
