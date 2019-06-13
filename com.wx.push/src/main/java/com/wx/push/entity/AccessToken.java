package com.wx.push.entity;

import java.util.Date;

public class AccessToken {

    //获取到的凭证
    private String token;
    //凭证有效时间，单位：秒
    private int expiresIn;
    //当前时间
    private Date time;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

}