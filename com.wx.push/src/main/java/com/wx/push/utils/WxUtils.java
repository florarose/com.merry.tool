package com.wx.push.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wx.push.constant.WxConstants;
import com.wx.push.entity.AccessToken;
import com.wx.push.entity.UserList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WxUtils {

    //日志
    private static Logger logger = LoggerFactory.getLogger(WxUtils.class);
    /*
       获取接口访问凭证
     */
    public static AccessToken getToken(String appid, String appsecret) {
        String requestUrl = WxConstants.token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
        // 发起GET请求获取凭证
        String url = HttpUtil.sendGet(requestUrl);
        if(!"".equals(url)&&null != url){
            JSONObject jsonObject = JSONObject.parseObject(url);
            if(null != jsonObject){
                try {
                   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    AccessTokenUtil.accessToken.setToken(jsonObject.getString("access_token"));
                    AccessTokenUtil.accessToken.setExpiresIn(jsonObject.getInteger("expires_in"));
                    AccessTokenUtil.accessToken.setTime(dateFormat.parse(dateFormat.format(new Date())));
                    if(null != AccessTokenUtil.accessToken){
                        logger.info("获取access_token成功，有效时长{}秒 token:{}", AccessTokenUtil.accessToken.getExpiresIn(), AccessTokenUtil.accessToken.getToken());
                    }
                }catch (Exception e) {
                    AccessTokenUtil.accessToken = null;
                    logger.error("{异常}", e);
                    logger.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInteger("errcode"), jsonObject.getString("errmsg"));
                }
            }
        }
        return AccessTokenUtil.accessToken;
    }
    /**
     * 获取关注的用户列表
     * @param token 调用接口凭证
     * @param openid 第一个拉取的OPENID
     * @return
     */
    public static UserList getUserList(String token, String openid){
        String requestUrl = WxConstants.userlist_url.replace("ACCESS_TOKEN",token).replace("NEXT_OPENID",openid);
        //发起GET请求获取凭证
        String url = HttpUtil.sendGet(requestUrl);
        if(!"".equals(url)&&null != url){
            JSONObject jsonObject = JSONObject.parseObject(url);
            if(null != jsonObject){
                try {
                    AccessTokenUtil.userList.setCount(jsonObject.getInteger("count"));
                    AccessTokenUtil.userList.setNext_openid(jsonObject.getString("next_openid"));
                    AccessTokenUtil.userList.setTotal(jsonObject.getInteger("total"));
                    JSONObject json1 = jsonObject.getJSONObject("data");
                    JSONArray json2 = JSONArray.parseArray(json1.get("openid").toString());
                    AccessTokenUtil.userList.setData(json2);
                    if (null != AccessTokenUtil.userList)
                        logger.info("获取用户列表成功， token:{}", AccessTokenUtil.userList.getCount(), AccessTokenUtil.userList.getTotal());
                    else{
                        logger.error("获取用户列表失败 errcode:{} errmsg:{}", jsonObject.getInteger("errcode"), jsonObject.getString("errmsg"));
                    }
                }catch (Exception e) {
                    logger.error("{异常}", e);
                    logger.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInteger("errcode"), jsonObject.getString("errmsg"));
                }
            }
        }
        return AccessTokenUtil.userList;
    }

    /**
     * 获取token的时间差
     * @param time 获取时间
     * @return
     */
    public static Long dateDiff(Date time){
        Date curretTime = new Date(System.currentTimeMillis());
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long ns = 1000;// 一秒钟的毫秒数
        long diff;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        try {
            diff = curretTime.getTime() - time.getTime();
            day = diff / nd;// 计算差多少天
            hour = diff % nd / nh + day * 24;// 计算差多少小时
            min = diff % nd % nh / nm + day * 24 * 60;// 计算差多少分钟
            sec = diff % nd % nh % nm / ns;// 计算差多少秒
            // 输出结果
            System.out.println("时间相差：" + day + "天" + (hour - day * 24) + "小时"
                    + (min - day * 24 * 60) + "分钟" + sec + "秒。");
            System.out.println("hour=" + hour + ",min=" + min);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hour;
    }


    /**
     * 全推送模板
     * @param openidList 全关注用户
     * @param url 推送接口
     */
    public static void push(List<String> openidList, String url,String access_token){
        url ="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN" ;
        url=  url.replace("ACCESS_TOKEN", access_token);
        for (int i = 0 ; i < openidList.size(); i ++){
            JSONObject json = new JSONObject();
            json.put("touser", openidList.get(i));//接收者wxName
            json.put("template_id", WxConstants.ce_template_id);//消息模板
            json.put("url", WxConstants.url);//填写url可查看详情
            System.out.println(json.toString());
            String js = HttpUtil.sendPost(url,null);
            JSONObject jsonObject = JSONObject.parseObject(js);
            logger.info("jsonObject",jsonObject);
            System.out.println("jsonObject=="+jsonObject);
        }
    }

    /**
     * 获取模板id
     */
    public static  String getTemplate(String access_token){
        String url ="https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token=ACCESS_TOKEN" ;
      url=  url.replace("ACCESS_TOKEN", access_token);
        String js = HttpUtil.sendPost(url,null);
        String templateId = "";
        if(null != js && !"".equals(js)){
            JSONObject jsonObject = JSONObject.parseObject(js);
            if(null != jsonObject){
                templateId = jsonObject.getString("template_id");
                String errs = jsonObject.getString("errmsg");
            }
        }
        return templateId;
    }
    /**
     * 获取模板列表
     */
    public static  String getTemplateList(String access_token){
        String url ="https://api.weixin.qq.com/cgi-bin/template/get_all_private_template?access_token=ACCESS_TOKEN" ;
        url=  url.replace("ACCESS_TOKEN", access_token);
        String js = HttpUtil.sendPost(url,null);
        String templateId = "";
        if(null != js && !"".equals(js)){
            JSONObject jsonObject = JSONObject.parseObject(js);
        }
        return templateId;
    }
}
