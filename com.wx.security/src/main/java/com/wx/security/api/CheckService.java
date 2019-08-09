package com.wx.security.api;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.wx.security.utils.HttpUtil;
import com.wx.security.utils.StringUtils;
import com.wx.security.utils.WXBizMsgCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liudongting
 * @date 2019/7/18 11:22
 */
public class CheckService {



    private static Logger logger = LoggerFactory.getLogger(CheckService.class);

    /**
     * 验证文本内容是否合格
     * @param content
     * @param accessToken
     * @return
     */
    public static Map<String,Object> msgSecCheck(String content, String accessToken){
        Map<String,Object> map = new HashMap<String,Object>();
        String url ="https://api.weixin.qq.com/wxa/msg_sec_check?access_token=ACCESS_TOKEN";
        url=url.replace("ACCESS_TOKEN",accessToken);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("content",content);
        String result= HttpUtil.sendPost(url,jsonObject.toJSONString());
        JSONObject jsonObjectMsg = JSONObject.parseObject(result);
        // 如果请求成功
        if (null != jsonObjectMsg) {
            try {
                String code =jsonObjectMsg.get("errcode")+"";
                String msg = jsonObjectMsg.getString("errmsg");
                map.put("code",code);
                map.put("msg",msg);
            } catch (JSONException e) {
                e.printStackTrace();
                // 获取token失败
                logger.error("内容验证失败 errcode:{} errmsg:{}", jsonObject.get("errcode"),
                        jsonObject.getString("errmsg"));
                map.put("code","调用失败");
            }
        }
        return map;
    }
    /**
     * 验证图片内容是否合格
     * @param multipartFile
     * @param accessToken
     * @return
     */
    public static Map<String,Object> imgSecCheck(String accessToken, MultipartFile multipartFile){
        Map<String,Object> map = new HashMap<String,Object>();
        String url ="https://api.weixin.qq.com/wxa/img_sec_check?access_token=ACCESS_TOKEN";
        url=url.replace("ACCESS_TOKEN",accessToken);
        // 如果请求成功
        try {
            JSONObject jsonObjectMsg= HttpUtil.doGetJson(url,multipartFile);
            if(null != jsonObjectMsg){
                String code =jsonObjectMsg.get("errcode")+"";
                String msg = jsonObjectMsg.getString("errmsg");
                map.put("code",code);
                map.put("msg",msg);
            }
        }catch (IOException e){
            e.printStackTrace();
            map.put("code","请求发送失败");
        } catch (JSONException e) {
            // 获取token失败
            map.put("code","调用失败");
        }
        return map;
    }

    /**
     * 异步验证语音文件
     * @param accessToken
     * @param mediaUrl
     * @param mediaType
     * @param id
     * @param type
     * @return
     */
    public static Map<String,Object> mediaCheckAsync(String accessToken,String mediaUrl,Integer mediaType,int id,int type){
        Map<String,Object> map = new HashMap<String,Object>();
        String url ="https://api.weixin.qq.com/wxa/media_check_async?access_token=ACCESS_TOKEN";
        url=url.replace("ACCESS_TOKEN",accessToken);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("media_url",mediaUrl);
        jsonObject.put("media_type",mediaType);
        JSONObject jsonObjectMsg=null;
        // 如果请求成功
        try {
            String result= HttpUtil.sendPost(url,jsonObject.toJSONString());
            jsonObjectMsg = JSONObject.parseObject(result);
            if(null != jsonObjectMsg){
                String code =jsonObjectMsg.get("errcode")+"";
                String msg = jsonObjectMsg.getString("errmsg");
                String trace_id = jsonObjectMsg.getString("trace_id");
                //这里把返回的信息存储到redis，也可以选择存储到数据库
//                if(code.equals("0")){
//                    Map<String,String> m = new HashMap<String,String>();
//                    m.put("id",id+"'");
//                    m.put("type",type+"");
//                    RedisUtils.set(trace_id,m);
//                }
                map.put("code",code);
                map.put("msg",msg);
            }
        } catch (JSONException e) {
            logger.error("内容验证失败 errcode:{} errmsg:{}",jsonObjectMsg.get("errcode"),
                    jsonObjectMsg.getString("errmsg"));
            e.printStackTrace();
            // 获取token失败
            map.put("code","调用失败");
        }
        return map;
    }
    /**
     * 接受微信服务器，语音验证的结果
     */
    /**
     * 验证消息的确来自微信服务器 ,首次验证是get请求，之后的验证都是post.所以这个接口可以接受两个方式的请求。
     */
    @RequestMapping(value= {"/getProcessRequest"},method= {RequestMethod.GET, RequestMethod.POST})
    public void authMessage(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> msg) throws Exception{
        boolean isGet=request.getMethod().toLowerCase().equals("get");
        if(isGet){
            // 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。  
            String signature = request.getParameter("signature");
            // 时间戳  
            String timestamp = request.getParameter("timestamp");
            // 随机数  
            String nonce = request.getParameter("nonce");
            // 随机字符串  
            String echostr = request.getParameter("echostr");
            PrintWriter out = null;
            if (! StringUtils.isNull(signature)||!StringUtils.isNull(timestamp)||!StringUtils.isNull(nonce)||!StringUtils.isNull(echostr))  {
                throw new IllegalArgumentException("请求参数非法，请核实!");
            }
            try {
                out = response.getWriter();
                // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，否则接入失败  
                if (HttpUtil.checkSignature(signature, timestamp, nonce)) {
                    System.out.println("成功");
                    out.print(echostr);
                    out.flush(); //必须刷新
                    logger.error("成功了。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
                }
                System.out.println("失败");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                out.close();
                out = null;
            }

        }else {
            // 检测结果，0：暂未检测到风险，1：风险
            logger.error("request "+msg);
            logger.error("msg: "+msg.get("Encrypt"));
            String mg=(String) msg.get("Encrypt");
            try {
                //对应的token, 加密字符串，小程序对应的appid ,前两个参数在小程序开发设置中，推送设置中查找。
                WXBizMsgCrypt pc = new WXBizMsgCrypt("xxx","xxx","xxxx");
                String result = pc.decrypt(mg);
                logger.error("result: "+result);
                JSONObject jsonObject =JSONObject.parseObject(result);
                logger.error("jsonObject: "+jsonObject);
                if(null != jsonObject){
                    String isrisky  = jsonObject.getString("isrisky");
                    String trace_id  = jsonObject.getString("trace_id");
                    logger.error("trace_id "+trace_id );
                    if(isrisky.equals("1")){
                        logger.error("检测到内容异常");
                          //根据检验结果处理对应的业务逻辑
                    }else if(isrisky.equals("0")) {
                        logger.error("内容正常，没有错误");
                    }
                }
            }catch (Exception e){
                e.printStackTrace();;
                logger.error("jiemi faile");
            }
        }
    }
}
