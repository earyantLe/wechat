package com.earyant.sys.token.service.impl;


import com.earyant.sys.token.dao.TokenMapper;
import com.earyant.sys.token.domain.Token;
import com.earyant.sys.token.service.WechatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("wechatService")
public class WechatServiceImpl implements WechatService {
    @Resource
    private TokenMapper tokenMapper;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void setToken(String key) {
        Token tokn = new Token();
        tokn.setId(0);
        tokn.setToken(key);
        tokenMapper.updateByPrimaryKey(tokn);
    }

    @Override
//    @Scheduled(cron = "0 0 0/1  * * ?")
    public void setTokens(String tokens) throws Exception {
//		Map<String, String> params = new HashMap<String, String>();
        //获取token执行体
//		params.put("grant_type", "client_credential");
//		params.put("appid", GlobalConstants.getInterfaceUrl("appid"));
//		params.put("secret", GlobalConstants.getInterfaceUrl("AppSecret"));
//		String jstoken = HttpUtils.sendGet(
//				GlobalConstants.getInterfaceUrl("tokenUrl"), params);
//        String access_token = tokens;
//        GlobalConstants.interfaceUrlProperties.put("access_token", tokens);


        Token tokn = new Token();
        tokn.setId(0);
        tokn.setToken(tokens);
        logger.error(tokens);
        int num = tokenMapper.updateByPrimaryKey(tokn);
        logger.error(num + "");
        Token t = tokenMapper.selectByPrimaryKey(0);
        logger.info("id:::" + t.getId() + "   token    " + t.getToken());
    }

}
