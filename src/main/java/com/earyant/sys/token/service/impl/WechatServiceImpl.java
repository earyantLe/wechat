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

    /**
     * 改方法已经失效了
     *
     * @param key token
     */
    @Override
    public void setToken(String key) {
        Token tokn = new Token();
        tokn.setId(0);
        tokn.setToken(key);
        tokenMapper.updateByPrimaryKey(tokn);
    }

    /**
     * 保存token到数据库中
     *
     * @param tokens 微信官方获取的token
     * @throws Exception 保存到数据失败
     */
    @Override
//    @Scheduled(cron = "0 0 0/1  * * ?")
    public void setTokens(Token tokens) throws Exception {
        logger.info(tokens.toString());
        tokenMapper.updateByPrimaryKey(tokens);
    }

}
