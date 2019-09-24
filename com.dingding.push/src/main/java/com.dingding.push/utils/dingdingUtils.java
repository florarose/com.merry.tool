package com.dingding.push.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;

public class dingdingUtils {

    private static Logger logger = LoggerFactory.getLogger(dingdingUtils.class);
    public static final String CORP_ID = "";
    public static final String CORP_SECRET = "";
    /**
     * 获取access_token
     */
    public static Map<String,Object> getAccess_token(){
         String ACCESS_TOKEN = "https://oapi.dingtalk.com/gettoken?corpid=CORPID&corpsecret=CORPSECRET";
          ACCESS_TOKEN = ACCESS_TOKEN.replace("CORPID",CORP_ID);
          ACCESS_TOKEN = ACCESS_TOKEN.replace("CORPSECRET",CORP_SECRET);
          String message = HttpUtil.sendGet(ACCESS_TOKEN);
          JSONObject jsonObject =JSONObject.parseObject(message);
          Map<String,Object> map = new HashMap<String, Object>();
          if(null != jsonObject){
              try{
                  String  access_token = (String) jsonObject.get("access_token");
                  Integer expiresIn =jsonObject.getInteger("expires_in");
                  Date date = new Date();
                  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                  String getAccessTokenTime = simpleDateFormat.format(date);
                  map.put("access_token",access_token);
                  map.put("expiresIn",expiresIn);
                  map.put("getAccessTokenTime",getAccessTokenTime);
              }catch (JSONException e){
                  logger.error("获取token失败 errcode:{} errmsg:{}", jsonObject.get("errcode"),
                          jsonObject.getString("errmsg"));
              }
          }
          return map;
    }

    /**
     * 获取部门列表
     * @param arrgs
     */
    public  static  List<Map<String,Object>> getDepartmentList(String accessToken){
        String departmentUrl = "https://oapi.dingtalk.com/department/list?access_token="+accessToken;
        String message = HttpUtil.sendGet(departmentUrl);
        JSONObject jsonObject =JSONObject.parseObject(message);
        List<Map<String,Object>>  alllist = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>>  list = null;
        if(null != jsonObject){
          JSONArray jsonArray = (JSONArray)jsonObject.get("department");
            for(int i=0;i<jsonArray.size();i++){
               JSONObject jsondepartment = (JSONObject) jsonArray.get(i); //获取部门列表中的某一个部门信息
                if(null != jsondepartment){
                     Integer departmetnId = jsondepartment.getInteger("id");
                     list = getDepartmentUser(departmetnId.toString(),accessToken);
                     alllist.addAll(list);
                    System.out.println("list"+list);
                }
        }
      }
        return alllist;
    }
        /**
         * 获取部门用户
         */
    public static List<Map<String,Object>> getDepartmentUser(String departmetnId,String accessToken){
        String departmentUrl = "https://oapi.dingtalk.com/user/simplelist?access_token=ACCESS_TOKEN&department_id=Id";
        departmentUrl = departmentUrl.replace("Id",departmetnId);
        departmentUrl = departmentUrl.replace("ACCESS_TOKEN",accessToken);
        String message = HttpUtil.sendGet(departmentUrl);
        JSONObject jsonObject =JSONObject.parseObject(message);

        List<Map<String,Object>>  list = new ArrayList<Map<String,Object>>();
        if(null != jsonObject){
            JSONArray jsonArray = (JSONArray)jsonObject.get("userlist");
            for(int i=0;i<jsonArray.size();i++){
                JSONObject jsonUser = (JSONObject) jsonArray.get(i);
                Map<String,Object> map = new HashMap<String, Object>();
                map.put("userid",jsonUser.get("userid"));
                map.put("name",jsonUser.get("name"));
                list.add(map);
            }

        }
        return list;
    }
    /**
     * 发送用户详情
     */
    public static JSONObject getUserDetail(String userId,String accessToken){
        String departmentUrl = "https://oapi.dingtalk.com/user/get?access_token=ACCESS_TOKEN&userid=zhangsan";
        departmentUrl = departmentUrl.replace("zhangsan",userId);
        departmentUrl = departmentUrl.replace("ACCESS_TOKEN",accessToken);
        String message = HttpUtil.sendGet(departmentUrl);
        JSONObject jsonObject =JSONObject.parseObject(message);
        if(null != jsonObject){

        }
        return jsonObject;
    }
    public  static  void main(String [] arrgs){
        Map<String,Object> message= getAccess_token();
        List<Map<String,Object>>  departmetnLsit= getDepartmentList((String) message.get("access_token"));
        for(int i=0;i<departmetnLsit.size();i++){
           if(departmetnLsit.get(i).get("name").equals("xxx")){
               String userId =(String) departmetnLsit.get(i).get("userid");
               JSONObject jsonObject =  getUserDetail(userId,(String) message.get("access_token"));
               String  bb =(String) jsonObject.get("hiredDate");
               System.out.println("bb"+bb);
               System.out.println("userId"+userId);
           }

        }
    }
}
