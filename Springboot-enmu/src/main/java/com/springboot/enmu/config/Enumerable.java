package com.springboot.enmu.config;


/**
 * @author liudongting
 * @date 2019/9/10 12:03
 */
public interface Enumerable {

    /**
     * 获取在i18n文件中对应的 i18nKey
     * @return i18nKey
     */
    String getKey();

    /**
     * 获取最终保存到数据库的值
     * @return 值
     */
    int getValue();

}
