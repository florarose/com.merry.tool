package com.wx.security.api;

import com.wx.security.utils.AesCbcUtil;

/**
 * @author liudongting
 * @date 2019/7/18 12:08
 */
public class Jiemi {
    /**
     * 小程序用户信息解密和手机号解密
     * @param data
     * @param key
     * @param iv
     */
    public void jiemi(String data, String key, String iv){
        try {
            String result = AesCbcUtil.decrypt(data,key,iv,"UTF-8");
        }catch (Exception e){

        }
    }

}
