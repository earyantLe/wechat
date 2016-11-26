package com.ctsig.sys.wechat.controller;

import com.earyant.Message;
import com.earyant.wechat.common.JSSDK_Config;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * ClassName: WeChatController
 *
 * @author dapengniao
 * @Description: 前端用户微信配置获取
 * @date 2016年3月19日 下午5:57:36
 */
@Controller
@RequestMapping("/wechatconfig")
public class WeChatController {
    private static Logger logger = Logger.getLogger(WeChatController.class);


    /**
     * @param @param  response
     * @param @param  request
     * @param @param  url
     * @param @throws Exception
     * @Description: 前端获取微信JSSDK的配置参数
     * @author dapengniao
     * @date 2016年3月19日 下午5:57:52
     */
    @RequestMapping("jssdk")
    public Message JSSDK_config(
            @RequestParam(value = "url", required = true) String url) {
        try {
//			System.out.println(url);
            Map<String, String> configMap = JSSDK_Config.jsSDK_Sign(url);
            return Message.success(configMap);
        } catch (Exception e) {
            logger.error(e, e);
            return Message.error();
        }
    }

}
