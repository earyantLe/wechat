package com.earyant.web.util;

import com.earyant.web.dao.TokenMappingMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Properties;

public class GlobalConstants {
    public static Properties interfaceUrlProperties;
    @Autowired
    TokenMappingMapper tokenMappingMapper;

    /**
     * @param @param  key
     * @param @return
     * @author dapengniao
     * @date 2015年10月13日 下午4:59:14
     */
    public static String getInterfaceUrl(String key) {
        return (String) interfaceUrlProperties.get(key);
    }

//    public  String getToken(String key) {
//        TokenMapping tokenMapping = tokenMappingMapper.selectByPrimaryKey(0);
//        return tokenMapping.getToken();
//    }
//
//    public  void setToken(String key) {
//        TokenMapping mapping = new TokenMapping();
//        mapping.setId(0);
//        mapping.setToken(key);
//        System.out.println("access_tokken  :: " + mapping.getToken());
//        tokenMappingMapper.updateByPrimaryKey(key);
//    }

}
