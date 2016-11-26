package com.earyant.sys.token.controller;

import com.earyant.sys.token.domain.Token;
import com.earyant.sys.token.service.WechatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class WechatController {
    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(WechatController.class);

    @Resource
    private WechatService wechatService;

    /**
     * 跳转到用户列表
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/wechat/wechat.action")
//	  @ResponseBody
    public void wechat(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        wechatService.setToken();
    }

    public void setToken(String key) {
        Token mapping = new Token();
        mapping.setId(0);
        mapping.setToken(key);
        System.out.println("access_tokken  :: " + mapping.getToken());
        wechatService.setToken(key);
    }

}
