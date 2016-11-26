package com.earyant.sys.wechat.service.impl;

import com.earyant.sys.gank.dao.DayMapper;
import com.earyant.sys.gank.dao.GankContentMapper;
import com.earyant.sys.token.service.WechatService;
import com.earyant.sys.wechat.service.GankService;
import com.earyant.sys.wechat.service.WeChatTaskService;
import com.earyant.web.util.GlobalConstants;
import com.earyant.wechat.util.HttpUtils;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: WeChatTaskImpl
 *
 * @author dapengniao
 * @Description: 微信两小时定时任务体
 * @date 2016年3月10日 下午1:42:29
 */
@Service("weChatTaskImpl")
public class WeChatTaskImpl implements WeChatTaskService {
    @Resource
    private DayMapper dayMapper;
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private GankContentMapper gankContentMapper;
    @Resource
    GankService gankService;
    @Resource
    WechatService wechatService;

    /**
     * @param @throws Exception
     * @Description: 任务执行体
     * @author dapengniao
     * @date 2016年3月10日 下午2:04:37
     */

    @Override
    public void getToken_getTicket() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        //获取token执行体
        params.put("grant_type", "client_credential");
        params.put("appid", GlobalConstants.getInterfaceUrl("appid"));
        params.put("secret", GlobalConstants.getInterfaceUrl("AppSecret"));
        String jstoken = HttpUtils.sendGet(
                GlobalConstants.getInterfaceUrl("tokenUrl"), params);
        String access_token = JSONObject.fromObject(jstoken).getString(
                "access_token"); // 获取到token并赋值保存
        GlobalConstants.interfaceUrlProperties.put("access_token", access_token);
        //获取jsticket的执行体
        params.clear();
        params.put("access_token", access_token);
        params.put("type", "jsapi");
        String jsticket = HttpUtils.sendGet(
                GlobalConstants.getInterfaceUrl("ticketUrl"), params);
        String jsapi_ticket = JSONObject.fromObject(jsticket).getString(
                "ticket");
        GlobalConstants.interfaceUrlProperties
                .put("jsapi_ticket", jsapi_ticket); // 获取到js-SDK的ticket并赋值保存
        String sql = "";
//				这里保存token信息
//		System.out.println("jsapi_ticket================================================" + jsapi_ticket);
//      System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "token为==============================" + access_token);
        wechatService.setTokens(access_token);
        //每个小时更新一下天数和
//        gankService.getDayAndContent();
    }
}
