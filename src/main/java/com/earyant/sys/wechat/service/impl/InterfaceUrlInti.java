package com.earyant.sys.wechat.service.impl;

import com.earyant.sys.wechat.service.InterfaceUrlIntiService;
import com.earyant.sys.wechat.service.WeChatTaskService;
import com.earyant.web.util.GlobalConstants;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * ClassName: InterfaceUrlInti
 *
 * @author dapengniao
 * @Description: 项目启动初始化方法
 * @date 2016年3月10日 下午4:08:21
 */
@Component
public class InterfaceUrlInti implements InterfaceUrlIntiService {

    @Resource
    private WeChatTaskService weChatTaskService;

    private static InterfaceUrlInti interfaceUrlInti;

    public void setUserInfo(WeChatTaskService weChatTaskService) {
        this.weChatTaskService = weChatTaskService;
    }

    @PostConstruct
    public void init() {
        interfaceUrlInti = this;
        interfaceUrlInti.weChatTaskService = this.weChatTaskService;

    }

    //    @Override
    public static void initDate() {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        Properties props = new Properties();
        if (GlobalConstants.interfaceUrlProperties == null) {
            GlobalConstants.interfaceUrlProperties = new Properties();
        }
        InputStream in = null;
        try {
            in = cl.getResourceAsStream("interface_url.properties");
            props.load(in);
            for (Object key : props.keySet()) {
                GlobalConstants.interfaceUrlProperties.put(key, props.get(key));
            }

            props = new Properties();
            in = cl.getResourceAsStream("wechat.properties");
            props.load(in);
            for (Object key : props.keySet()) {
                GlobalConstants.interfaceUrlProperties.put(key, props.get(key));
            }

//            WeChatTaskImpl timer = new WeChatTaskImpl();
            interfaceUrlInti.weChatTaskService.getToken_getTicket();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return;
    }

}
