package com.springboot.enmu.config;

/**
 * @author liudongting
 * @date 2019/9/10 12:03
 */
public enum PlatformType implements Enumerable {

    android(1,"android"),
    ios(0,"ios");

    private int value;
    private String key;


    PlatformType(int value, String key) {
        this.value = value;
        this.key = key;
    }


    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public int getValue() {
        return this.value;
    }

    public Integer ofInt(String value){
        if(value==android.getKey()){
            return android.getValue();
        }else if(value==ios.getKey()){
            return ios.getValue();
        }
        return null;
    }
}

