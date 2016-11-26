package com.earyant.web.util;

import com.ctsig.sys.token.dao.TokenMapper;
import com.ctsig.sys.token.domain.Token;
import com.ctsig.sys.token.service.WechatService;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.Properties;
@Controller
public class GlobalConstants {
    public static Properties interfaceUrlProperties;
    @Resource
    private TokenMapper tokenMapper;
    @Resource
    private static WechatService wechatService;
    /**
     * @param @param  key
     * @param @return
     * @author dapengniao
     * @date 2015年10月13日 下午4:59:14
     */
    public static String getInterfaceUrl(String key) {
        return (String) interfaceUrlProperties.get(key);
    }

    public  String getToken(String key) {
//        TokenMapping tokenMapping = tokenMappingMapper.selectByPrimaryKey(0);
        return tokenMapper.selectByPrimaryKey(0).getToken();
//        return "";
    }

    public static void setToken(String key) {
        Token mapping = new Token();
        mapping.setId(0);
        mapping.setToken(key);
        System.out.println("access_tokken  :: " + mapping.getToken());
        wechatService.setToken(key);
    }

}
