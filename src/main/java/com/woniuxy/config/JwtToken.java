package com.woniuxy.config;


import org.apache.shiro.authc.AuthenticationToken;

// token类，注意需要实现Token
public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
