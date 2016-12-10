package com.earyant.common.utils;

import com.earyant.CommonConstants;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

public class SendPhoneUtils {
    /**
     * 设置短信相关信息
     *
     * @param phoneNum
     * @param result
     * @return
     * @throws JSONException
     */
    public static JSONObject getHttpHeads(String phoneNum, String context) throws JSONException {
        JSONObject o = new JSONObject();
        o.put("pszMobis", phoneNum);
        o.put("iMobiCount", "1");
        o.put("pszMsg", context);
        o.put("pszSubPort", "*");
        return o;
    }

    public static String SendPhoneNumberValidate(String phoneNum, String context) throws Exception {
        //短息地址
        String url = PropertisUtils.getProperty(CommonConstants.SEND_EMAIL_PROP_PATH, "MSGUrl");
        RestTemplate restTemplate = new RestTemplate();
        //设置短信相关信息
        JSONObject o = getHttpHeads(phoneNum, context);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(o.toString(), headers);
        String ss = restTemplate.postForObject(url, entity, String.class);    //return true/false;
        return ss;
    }

    /**
     * 根据长度生成长度随机生成验证码
     *
     * @param 验证码长度
     * @return 验证码
     */
    public static String getPhoneValidate(Integer j) {
        Random random = new Random();
        String result = "";
        for (int i = 0; i < j; i++) {
            result += random.nextInt(10);
        }
        return result;
    }
}
