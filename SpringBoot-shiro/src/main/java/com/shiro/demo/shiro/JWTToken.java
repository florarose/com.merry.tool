package com.shiro.demo.shiro;

import org.apache.shiro.authc.HostAuthenticationToken;


/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/4/21 14:48
 */
public class JWTToken implements HostAuthenticationToken {
    private String token;
    private String host;

    public JWTToken(String token) {
        this.token = token;
//        this.host = "2";
//       this(token, null);
    }

    public JWTToken(String token, String host) {
        this.token = token;
        this.host = host;
    }

    public String getToken() {
        return this.token;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public String toString() {
        return token + ':' + host;
    }
}
