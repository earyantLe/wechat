package com.earyant.sys.token.domain;

public class Token {
    private Integer id;

    private String token;
    private String appid;
    private String appSecret;

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {

        return "id 是 " + id + "，token是" + token + "，appid是" + appid + "，appsecret是+" + appSecret + "";
    }
}