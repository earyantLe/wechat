
package com.earyant.web.start;

import com.earyant.web.util.GlobalConstants;
import com.earyant.wechat.common.WeChatTask;

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
public class InterfaceUrlInti {

    public synchronized static void init() {
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

            WeChatTask timer = new WeChatTask();
            timer.getToken_getTicket();

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
