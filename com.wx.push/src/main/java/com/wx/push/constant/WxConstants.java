package com.wx.push.constant;

public interface WxConstants {

    /**
     * 爱照护
     */
    public static final  String APPID = "wxa1cad50806c2d4ef";
    public static final  String APPSECRET = "c3d2779a503f2c0e499d9c7448648f1d";

    //获取调用凭证地址
    public final static String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET ";

    //发送模板信息接口
    public final static String send_url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";

    //获得关注用户id
    public final static String userlist_url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID ";

    // 模板id
    public static final  String ce_template_id = "ppG4LrPYwnt3PIgVUWyFCq9mx6YgM90ofNBIlqTmuRY";

    //模板的链接 url
    public static final  String url = "";

}
