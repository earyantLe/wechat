package com.earyant.sys.wechat.service.impl;

import com.earyant.sys.gank.dao.DayMapper;
import com.earyant.sys.gank.dao.GankContentMapper;
import com.earyant.sys.token.dao.TokenMapper;
import com.earyant.sys.token.domain.Token;
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
import java.util.List;
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
    @Resource
    TokenMapper tokenMapper;

    /**
     * @param @throws Exception
     * @Description: 任务执行体
     * @author dapengniao
     * @date 2016年3月10日 下午2:04:37
     */

    @Override
    public void getToken_getTicket() throws Exception {

        List<Token> tokens = tokenMapper.selectAll();
        tokens.forEach((Token o) -> {
            Map<String, String> params = new HashMap<String, String>();
            //获取token执行体
            params.put("grant_type", "client_credential");
            params.put("appid", o.getAppid());
//            params.put("appid", GlobalConstants.getInterfaceUrl("appid"));
            params.put("secret", o.getAppSecret());
//            params.put("secret", GlobalConstants.getInterfaceUrl("AppSecret"));
            String jstoken = null;
            try {
                jstoken = HttpUtils.sendGet(
                        GlobalConstants.getInterfaceUrl("tokenUrl"), params);
            } catch (Exception e) {
                logger.info("获取token失败，原因是：" + e.toString());
            }
            String access_token = JSONObject.fromObject(jstoken).getString(
                    "access_token"); // 获取到token并赋值保存
            GlobalConstants.interfaceUrlProperties.put("access_token", access_token);
            //获取jsticket的执行体
            params.clear();
            params.put("access_token", access_token);
            params.put("type", "jsapi");
            String jsticket = null;
            try {
                jsticket = HttpUtils.sendGet(
                        GlobalConstants.getInterfaceUrl("ticketUrl"), params);
            } catch (Exception e) {
                logger.info("获取ticket失败，失败原因是：" + e.toString());
            }
            String jsapi_ticket = JSONObject.fromObject(jsticket).getString(
                    "ticket");
            GlobalConstants.interfaceUrlProperties
                    .put("jsapi_ticket", jsapi_ticket); // 获取到js-SDK的ticket并赋值保存
            String sql = "";
//				这里保存token信息
            try {
                o.setToken(access_token);
                wechatService.setTokens(o);
            } catch (Exception e) {
                logger.info("保存token到数据库失败，失败原因是：" + e.toString());
            }
            //每个小时更新一下天数和
//        gankService.getDayAndContent();
        });
    }
}
