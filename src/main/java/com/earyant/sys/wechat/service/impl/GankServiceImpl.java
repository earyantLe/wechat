package com.earyant.sys.wechat.service.impl;


import com.earyant.sys.gank.dao.AllContentBeanMapper;
import com.earyant.sys.gank.dao.DayMapper;
import com.earyant.sys.gank.dao.GankContentMapper;
import com.earyant.sys.gank.domain.AllContentBeanWithBLOBs;
import com.earyant.sys.gank.domain.GankContent;
import com.earyant.sys.wechat.service.GankService;
import com.earyant.web.util.GlobalConstants;
import com.earyant.wechat.bean.AllContent;
import com.earyant.wechat.bean.Content;
import com.earyant.wechat.bean.Day;
import com.earyant.wechat.bean.list.AllContentList;
import com.earyant.wechat.util.HttpUtils;
import com.earyant.wechat.util.JsonStringUtils;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by lirui on 2016/11/4.
 */
@Service("gankServiceImpl")
public class GankServiceImpl implements GankService {
    @Resource
    private GankContentMapper gankContentMapper;
    @Resource
    private DayMapper dayMapper;
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    AllContentBeanMapper allContentBeanMapper;

    public static String DateToString(Date date, String datefmt) {
        if (null == date) return "";
        if (null == (datefmt) && "".equals(datefmt)) datefmt = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat f = new SimpleDateFormat(datefmt);
        return f.format(date);
    }

    @Override
    public String getOneDayContent(Map<String, String> map) throws Exception {
        String date = map.get("day");
        String getOneDayContentUrl = (String) GlobalConstants.interfaceUrlProperties.get("getOneDayContent");
        Map<String, String> param = new HashMap<String, String>();
        String getOneDayContentUrls = getOneDayContentUrl + date;
        String content = null;
        try {
            List<GankContent> gankContent = gankContentMapper.selectByNewDate(date);
            if (gankContent.size() > 0) {
                content = gankContent.get(0).getContent();
            } else {
                content = HttpUtils.sendGet(getOneDayContentUrls, param);
                Content content1 = JsonStringUtils.jsonStringToObject(content, Content.class);
                content = content1.getResults().get(0).getContent();
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
        }
        return content;
    }

    @Override
    public void getDayAndContent() {
        //先获取所有日期。
        String getHistoryDate = GlobalConstants.getInterfaceUrl("gank_history");
        Map<String, String> historyDateParam = new HashMap<String, String>();
        String date;
        try {
            String historyDateContent = HttpUtils.sendGet(getHistoryDate, historyDateParam);
            logger.debug(historyDateContent);
            Gson gson = new Gson();
            Day days = gson.fromJson(historyDateContent, Day.class);
            List<String> day = days.getString();
            if (day.size() > 0) {
//                String d = day[i];
                for (int i = 0; i < day.size(); i++) {
                    String d = day.get(i);
                    date = d.substring(0, 4) + "/" + d.substring(5, 7) + "/" + d.substring(8, d.length());//2016-11-18
                    System.out.println(day.get(i));
                    com.earyant.sys.gank.domain.Day da = new com.earyant.sys.gank.domain.Day();
                    da.setDayDate(date);
//                    com.earyant.sys.gank.domain.Day dayIsExist = dayMapper.selectBydate(date);
                    //所有的Day日期保存到数据库中
//                    if (CommMethod.isEmpty(dayIsExist)) {
                    dayMapper.insert(da);
//                    } else {
//                        da.setId(dayIsExist.getId());
//                        dayMapper.updateByPrimaryKey(da);
//                    }
                    //将每天的content保存在数据库中
//                    String getOneDayContentUrl = GlobalConstants.getInterfaceUrl("getOneDayContent");
//                    String getOneDayContentUrls = getOneDayContentUrl + date;
//                    Map<String, String> param = new HashMap<String, String>();
//                    String content = HttpUtils.sendGet(getOneDayContentUrls, param);
//                    Content content1 = JsonStringUtils.jsonStringToObject(content, Content.class);
//                    GankContent gankContent1;
//                    if (content1.getResults().size() > 0) {
//
//                    } else {
//                        getOneDayContentUrls = getOneDayContentUrl + date;
//                        content = HttpUtils.sendGet(getOneDayContentUrls, param);
//                        content1 = JsonStringUtils.jsonStringToObject(content, Content.class);
//                    }
//                    gankContent1 = new GankContent();
//                    gankContent1.setContent(content1.getResults().get(0).getContent());
//                    gankContent1.setDate(date);
//                    gankContent1.setTitle(content1.getResults().get(0).getTitle());
//                    Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*\\\\['\"]([^'\"]+)['\"][^>]*>");//<img[^<>]*src=[\'\"]([0-9A-Za-z.\\/]*)[\'\"].(.*?)>");
//                    Matcher m = p.matcher(content);
//                    System.out.println(m.group(1));
//                    gankContent1.setImgUrl(m.group(1).substring(0, m.group(1).length() - 1));
//                    gankContentMapper.insert(gankContent1);
                }
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getAllContent(Map<String, String> map) {
//        map.get("");
        String result = "success";
        int page;
        page = 1;
        while (page < 4) {
            int num = 50;
            logger.info("num===" + num + " page == " + page + "  result== " + result);
            result = getContent(num, page);
            page += 1;
        }

    }

    private String getContent(int num, int page) {
        String allContentUrl = GlobalConstants.getInterfaceUrl("allContent");
        String url = allContentUrl + "all" + "/" + num + "/" + page;
        logger.info(url);
        Map<String, String> param = new HashMap<String, String>();
        String content = "";
        try {
            content = HttpUtils.sendGet(url, param);
//            String str = new String(content.getBytes(), "gb2312");
            logger.info(content);
            Gson gson = new Gson();
            AllContent allContent = gson.fromJson(content, AllContent.class);
//            AllContent allContent = JsonStringUtils.jsonStringToObject(content, AllContent.class);
            List<AllContentList> allContentList = allContent.getResults();
            for (int i = 0; i < allContentList.size(); i++) {
                String desc = allContentList.get(i).getDesc();
                List<String> imgUrls = allContentList.get(i).getString();
                String gankId = allContentList.get(i).get_id();
                String createdAt = allContentList.get(i).getCreatedAt();
                String publishedAt = allContentList.get(i).getPublishedAt();
                String source = allContentList.get(i).getSource();
                String type = allContentList.get(i).getType();
                String goToUrl = allContentList.get(i).getUrl();
                boolean used = allContentList.get(i).getUsed();
                String who = allContentList.get(i).getWho();

                AllContentBeanWithBLOBs allContentBeanWithBLOBs1 = new AllContentBeanWithBLOBs();
                if (null != imgUrls && !"".equals(imgUrls))
                    allContentBeanWithBLOBs1.settImages(imgUrls.toString());
                allContentBeanWithBLOBs1.settUrl(goToUrl);
                allContentBeanWithBLOBs1.settContentId(gankId);
                allContentBeanWithBLOBs1.settCreatedat(createdAt);
                allContentBeanWithBLOBs1.settDesc(desc);
                allContentBeanWithBLOBs1.settPublishedat(publishedAt);
                allContentBeanWithBLOBs1.settSource(source);
                allContentBeanWithBLOBs1.settType(type);
                allContentBeanWithBLOBs1.settUsed(used + "");
                allContentBeanWithBLOBs1.settWho(who);
//                AllContentBeanWithBLOBs allContentBeanWithBLOBs = allContentBeanMapper.selectByGankId(gankId);
//                int resultNum;
//                if (!CommMethod.isEmpty(allContentBeanWithBLOBs)) {
//                    allContentBeanMapper.updateByGankIdWithBLOBs(allContentBeanWithBLOBs1);
//                    logger.info("更新成功");
//                } else {
                int resultNum = allContentBeanMapper.insert(allContentBeanWithBLOBs1);
                logger.info(resultNum + "   插入成功+1");
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }

        return "success";
    }
}
