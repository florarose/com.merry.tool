package com.wx.push.entity;

import com.alibaba.fastjson.JSONArray;

public class UserList {
    //关注该公众账号的总用户数
    private int total;
    //拉取的OPENID个数，最大值为10000
    private int count;
    //列表数据，OPENID的列表
    private JSONArray data;
    //拉取列表的最后一个用户的OPENID
    private String next_openid;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public JSONArray getData() {
        return data;
    }

    public void setData(JSONArray data) {
        this.data = data;
    }

    public String getNext_openid() {
        return next_openid;
    }

    public void setNext_openid(String next_openid) {
        this.next_openid = next_openid;
    }
}
