package com.earyant.sys.wechat.controller;

import com.earyant.sys.wechat.service.EventDisPatcherService;
import com.earyant.sys.wechat.service.GankService;
import com.earyant.web.util.GlobalConstants;
import com.earyant.web.utils.CommMethod;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by lirui on 2016/11/2.
 */
@Controller
@RequestMapping("/gank")
public class GankController {
    private static Logger logger = Logger.getLogger(WeChatController.class);
    @Resource
    GankService gankService;
    @Resource
    EventDisPatcherService eventDisPatcherService;

    //    @Resource
//    private GankService gankService;
    //    @RequestParam(value = "signature", required = true) String signature,
//    @RequestParam(value = "timestamp", required = true) String timestamp,
//    @RequestParam(value = "nonce", required = true) String nonce,
//    @RequestParam(value = "echostr", required = true) String echostr
    @RequestMapping(value = "getHistory.action", method = RequestMethod.GET)
    public void doGet(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
//        gankService.getDat();
        String url = GlobalConstants.getInterfaceUrl("gank_history");
        String result = "";
        gankService.getDayAndContent();
//                HttpUtils.sendGet(url, null);
//        Day days = JsonStringUtils.jsonStringToObject(result, Day.class);
//        Gson gson = new Gson();
//        Day days = gson.fromJson(result, Day.class);
//        for (int i = 0; i < days.getString().size(); i++) {
//            String day = days.getString().get(i);
//            List<Map<String, Object>> daySize = this.getJdbcService().findForList("select day from day where day = ? ", new Object[]{day});
//            if (daySize.size() > 0) {
//                this.getJdbcService().update("", new Object[]{});
//            } else {
//                this.getJdbcService().update("insert into  day (day) values (?) ;", new Object[]{day});
//            }
//        }
    }

    @RequestMapping("getOneDayContent.action")
//    @ResponseBody
    public String getOneDayContent(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        String path = request.getContextPath();
        Map<String, String> map = CommMethod.getRequestMap(request);
        String result = gankService.getOneDayContent(map);
        request.getSession().setAttribute("content", result);
        return "wechat/allContent";
    }

    @RequestMapping("saveAllContent2DB.action")
    public String saveAllContent2DB(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        Map<String, String> map = CommMethod.getRequestMap(request);
        gankService.getAllContent(map);
        return "";
    }

}
