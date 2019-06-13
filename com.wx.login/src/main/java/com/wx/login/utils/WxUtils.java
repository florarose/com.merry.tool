package com.wx.login.utils;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.wx.login.entity.WxConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.util.Date;

public class WxUtils {

    private static Logger logger = LoggerFactory.getLogger(WxUtils.class);
    /**
     * 第一步：用户同意授权，获取code(引导关注者打开如下页面：)
     * 获取 code、state
     */
    public static String getCode(String redirectUrl){
        String authUrl = WxConstants.AUTH_BASE_URL + "appid=" + WxConstants.APPID
                + "&redirect_uri=" + URLEncoder.encode(redirectUrl)
                + "&response_type=code"
                + "&scope=" + WxConstants.SCOPE
                + "&state=STATE#wechat_redirect";
        return authUrl;
    }

    /**
     * 获取access_token、openid,在获取用户信息
     * 第二步：获取用户信息
     * @param code url = "https://api.weixin.qq.com/sns/oauth2/access_token
     *   ?appid=APPID
     *   &secret=SECRET
     *   &code=CODE
     *   &grant_type=authorization_code"
     * */
    public static JSONObject getAccess_token(String code,String secret){
        String authUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code ";
        authUrl= authUrl.replace("APPID", WxConstants.APPID);
        authUrl = authUrl.replace("SECRET", WxConstants.APPSECRET);
        authUrl = authUrl.replace("CODE", code);
        //向微信发送请求,获取信息 openid
        JSONObject object = null;
        String  url= HttpUtil.sendGet(authUrl);
        if(null!= url && !"".equals(url)){
            JSONObject obj = JSONObject.parseObject(url);
            if(null != obj){
                String token = obj.getString("access_token");
                String openId = obj.getString("openid");
                Integer expiresIn = (Integer) obj.get("expires_in");
                String refreshToken =(String) obj.get("refresh_token");
                Date date = new Date(); //记录获取token的时间
                if( (null != token && !"".equals(token)) && (null !=openId && !"".equals(openId))&&(null != refreshToken&& !"".equals(refreshToken))){
                   object = getUserInfo(token,openId,refreshToken);
                }
            }
        }
        return object;
    }

    /**测试token
     * @return
     */
    public static String check(String token,String openId){
        //测试token是否有效
        String accessUrl = "https://api.weixin.qq.com/sns/auth?access_token=ACCESS_TOKEN&openid=OPENID";
        accessUrl= accessUrl.replace("ACCESS_TOKEN", token);
        accessUrl= accessUrl.replace("OPENID", openId);
        String  url= HttpUtil.sendGet(accessUrl);
        String msg="";
        if(null != url && !"".equals(url)){
            JSONObject tokenJsonObject = JSONObject.parseObject(url);
            if(null!=tokenJsonObject){
                msg= tokenJsonObject.getString("errmsg");
            }
        }
        return msg;
    }

    /**
     * 刷新token
     * @return
     */
    public static String updateToken(String refreshToken){
        String authUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
        authUrl= authUrl.replace("APPID", WxConstants.APPID);
        authUrl = authUrl.replace("REFRESH_TOKEN", refreshToken);
        String  url= HttpUtil.sendGet(authUrl);
        String access_token="";
        if(null != url && !"".equals(url)){
            JSONObject jsonObject = JSONObject.parseObject(url);
            if (null != jsonObject) {
                try {
                    access_token=jsonObject.getString("access_token");
                } catch (JSONException e) {
                    // 获取token失败
                    logger.error("获取token失败 errcode:{} errmsg:{}", jsonObject.get("errcode"),
                            jsonObject.getString("errmsg"));
                }
            }
            return access_token;
        }
        return null;
    }

    /**
     * 获取用户信息
     */
    public static JSONObject  getUserInfo(String token,String openId,String refreshToken){
        String msg=check(token,openId);
        if("ok".equals(msg)){
            token=token;
        }else{
            String newToken=updateToken(refreshToken);
            token=newToken;
        }
        String authUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
        authUrl= authUrl.replace("OPENID", openId);
        authUrl = authUrl.replace("ACCESS_TOKEN", token);
        String  url=HttpUtil.sendGet(authUrl);
        JSONObject object = null;
        if(null != url && !"".equals(url)){
            object = JSONObject.parseObject(url);
        }
        return object;
    }
}
