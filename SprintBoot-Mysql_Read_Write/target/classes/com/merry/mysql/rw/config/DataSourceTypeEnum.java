package com.merry.mysql.rw.config;

/**
 * @auther: 坎布里奇
 * @Version: 1.0.0V
 * @date: 2020/9/11 14:22
 * @Description: mm
 */
public enum DataSourceTypeEnum {


    DATA_SOURCE_MASTER(1,"master"),
    DATA_SOURCE_SLAVE(2,"slave");

    DataSourceTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    private Integer code;

    private String name;

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
