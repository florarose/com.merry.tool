package com.redis.study.pojo;

import lombok.Data;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/4/24 15:10
 */
@Data
public class Goods {

    private Integer id;
    private String name;
    private Integer type;
    private String content;
}
