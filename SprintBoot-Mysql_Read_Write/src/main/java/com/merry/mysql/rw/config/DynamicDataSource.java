package com.merry.mysql.rw.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @auther: 坎布里奇
 * @Version: 1.0.0V
 * @date: 2020/9/11 14:21
 * @Description: mm
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDatabaseType();
    }

}
