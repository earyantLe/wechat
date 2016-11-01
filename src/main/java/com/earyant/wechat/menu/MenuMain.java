package com.earyant.wechat.menu;

import com.alibaba.fastjson.JSONObject;
import com.earyant.web.util.GlobalConstants;
import com.earyant.wechat.util.HttpUtils;

import net.sf.json.JSONArray;

public class MenuMain {

    public static void main(String[] args) {

        ClickButton cbt = new ClickButton();
        cbt.setKey("image");
        cbt.setName("发张自拍！");
        cbt.setType("pic_photo_or_album");


        ViewButton vbt = new ViewButton();
        vbt.setUrl("https://earyant.github.io/");
        vbt.setName("博客");
        vbt.setType("view");

        JSONArray sub_button = new JSONArray();
        sub_button.add(cbt);
        sub_button.add(vbt);


        JSONObject buttonOne = new JSONObject();
        buttonOne.put("name", "菜单");
        buttonOne.put("sub_button", sub_button);

        JSONArray button = new JSONArray();
        button.add(vbt);
        button.add(buttonOne);
        button.add(cbt);

        JSONObject menujson = new JSONObject();
        menujson.put("button", button);
//		System.out.println(menujson);
        //这里为请求接口的url   +号后面的是token，这里就不做过多对token获取的方法解释
        String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="
                + GlobalConstants.getInterfaceUrl("access_token");
//				+"usorCQoJ2uxNAqb1ct_Iu6JqCMNzvfHYx3NgWGI77OyTpr8LxelFdaGiSmTvc64Pl27hyaEuZxJ8qrq6KxhFaD5ImGVxY8cA0vjyM0lt0DgHEepWk2f6UskdwjrclVS1UVNfAFAQRJ";

        try {
            String rs = HttpUtils.sendPostBuffer(url, menujson.toJSONString());
            System.out.println(rs);
        } catch (Exception e) {
            System.out.println("请求错误！");
        }

    }

}
