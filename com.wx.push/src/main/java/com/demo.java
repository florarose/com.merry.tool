package com;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wx.push.entity.AccessToken;
import com.wx.push.entity.UserList;
import com.wx.push.utils.WxUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class demo {

   static String sendurl ="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
    public static  void sendMessage(){
        Class  beanClass =null ;
        AccessToken accessToken = WxUtils.getToken("","");
        UserList userList= WxUtils.getUserList(accessToken.getToken(),"");
//        JSONArray array = userList.getData();
    //;    UserList userLists =  WxUtils.getUserLists(accessToken.getToken(),userList.toJSONString());

//        List<String> list =  JSONObject.parseArray(array.toJSONString(),beanClass);
//        WxUtils.push(list,sendurl,accessToken.getToken());
        System.out.println("user"+userList.getNext_openid()+"----"+userList.getCount()+"----"+userList.getData());
    }
    public static void main(String [] args){
       gettemplate();
    }

    public static  void  gettemplate(){
//        AccessToken accessToken = WxUtils.getToken("","");
//        String aa = WxUtils.getTemplate(accessToken.getToken());
//        String aann = WxUtils.getTemplateList(accessToken.getToken());
//
//         sendMessage();
//        Class  beanClass =null ;
//        String s= WxUtils.getTemplate("33_wORbNSnxMoIQSq8NFAjSuZ16VbhDKoVG5Gd8-jscbMIgwtCbQp-tk02xGz1Ebk1QbdVLXacDm16P1mVuaQfx4ihuKL-vjveqbdRnGJw7vb-ulqDHip1nF5rSkUVJHNQRAjWCO066mHhfOu23VEKaACAPYE");
//
//        System.out.println("user"+s);
        senddd();
    }


    public  static  void senddd(){
        AccessToken accessToken = WxUtils.getToken("","");
        UserList userList= WxUtils.getUserList(accessToken.getToken(),"");
        System.out.println(userList.getData().toJSONString());
        List<JSONObject> opends = new ArrayList<JSONObject>();
        for (int i=0; i<userList.getData().size();i++){
            String a = (String) userList.getData().get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("openid",a);
            opends.add(jsonObject);
        }
//        JSONObject jsonObject = new JSONObject();
//        JSONObject jsonObject2 = new JSONObject();
         JSONObject jsonObjects = new JSONObject();
//        jsonObject.put("openid","oijKq1RWCmiDsSwgbbUhHzvEJ0lw");
//        jsonObject2.put("openid","oijKq1RxwdgeIbRHvrJPMn1xMOA8");
        jsonObjects.put("user_list",opends);
//        opends.add(jsonObject);
//        opends.add(jsonObject2);
        UserList userLists =  WxUtils.getUserLists(accessToken.getToken(),jsonObjects.toJSONString());
        System.out.println("1:::::"+userLists.getData());
        List<String> opendss = new ArrayList<String>();
        opendss.add("oijKq1RWCmiDsSwgbbUhHzvEJ0lw");
        WxUtils.push(opendss,accessToken.getToken());
    }
}
