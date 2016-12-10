package com.earyant.sys.token.service;


import com.earyant.sys.token.domain.Token;

public interface WechatService {
    void setToken(String key);

    public void setTokens(Token tokens) throws Exception;
}
