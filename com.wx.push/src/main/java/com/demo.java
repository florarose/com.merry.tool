package com;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wx.push.entity.AccessToken;
import com.wx.push.entity.UserList;
import com.wx.push.utils.WxUtils;

import java.util.ArrayList;
import java.util.List;

public class demo {

   static String sendurl ="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
    public static  void sendMessage(){
        Class  beanClass =null ;
        AccessToken accessToken = WxUtils.getToken("","");
        UserList userList= WxUtils.getUserList(accessToken.getToken(),"");
        JSONArray array = userList.getData();
        List<String> list =  JSONObject.parseArray(array.toJSONString(),beanClass);
        WxUtils.push(list,sendurl,accessToken.getToken());
        System.out.println("user"+userList.getNext_openid()+"----"+userList.getCount()+"----"+userList.getData());
    }
    public static void main(String [] args){
       gettemplate();
    }

    public static  void  gettemplate(){
        AccessToken accessToken = WxUtils.getToken("","");
//        String aa = WxUtils.getTemplate(accessToken.getToken());
        String aann = WxUtils.getTemplateList(accessToken.getToken());

        System.out.println("template" + aann);
    }


    public  static  void senddd(){
        Class  beanClass =null ;
        UserList userList = new UserList();
        String a = "";
        JSONObject jsonObject = JSONObject.parseObject("");
        JSONArray json = JSONArray.parseArray(jsonObject.toString());
        userList.setData(json);
        JSONArray array = userList.getData();
        List<String> list = new ArrayList<String>();
        for(Object jstr:json){
            list.add((String)jstr);
        }
    }
}
