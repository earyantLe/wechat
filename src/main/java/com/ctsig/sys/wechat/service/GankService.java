package com.ctsig.sys.wechat.service;

import java.util.Map;

/**
 * Created by lirui on 2016/11/4.
 */
public interface GankService {
    String getOneDayContent(Map<String, String> map) throws Exception;

    public void getDayAndContent();

    void getAllContent(Map<String, String> map);
}
