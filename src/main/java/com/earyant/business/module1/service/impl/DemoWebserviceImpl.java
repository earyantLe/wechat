package com.earyant.business.module1.service.impl;

import com.earyant.business.module1.service.DemoWebservice;
import org.springframework.stereotype.Service;


@Service("demoWebservice")
public class DemoWebserviceImpl implements DemoWebservice {


    public String getDevices(String user) {
//		System.out.print("hello");
//		return CommonUtils.toJson(user);
        return "你好";
    }

}
