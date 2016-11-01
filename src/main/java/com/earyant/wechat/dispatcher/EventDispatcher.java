package com.earyant.wechat.dispatcher;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.earyant.wechat.common.WeChatTask;
import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

import com.earyant.wechat.common.GetUseInfo;
import com.earyant.wechat.message.resp.Article;
import com.earyant.wechat.message.resp.Image;
import com.earyant.wechat.message.resp.ImageMessage;
import com.earyant.wechat.message.resp.NewsMessage;
import com.earyant.wechat.util.HttpPostUploadUtil;
import com.earyant.wechat.util.MessageUtil;

/**
 * ClassName: EventDispatcher
 *
 * @author dapengniao
 * @Description: 事件消息业务分发器
 * @date 2016年3月7日 下午4:04:41
 */
public class EventDispatcher {
    private static Logger logger = Logger.getLogger(EventDispatcher.class);

    public static String processEvent(Map<String, String> map) throws Exception {
        String openid = map.get("FromUserName"); // 用户openid
        String mpid = map.get("ToUserName"); // 公众号原始ID

        ImageMessage imgmsg = new ImageMessage();
        imgmsg.setToUserName(openid);
        imgmsg.setFromUserName(mpid);
        imgmsg.setCreateTime(new Date().getTime());
        imgmsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_Image);

        //对图文消息
        NewsMessage newmsg = new NewsMessage();
        newmsg.setToUserName(openid);
        newmsg.setFromUserName(mpid);
        newmsg.setCreateTime(new Date().getTime());
        newmsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) { // 关注事件
//			System.out.println("==============这是关注事件！");
            try {
//                WeChatTask timer = new WeChatTask();
//                timer.getToken_getTicket();
                HashMap<String, String> userinfo = GetUseInfo.Openid_userinfo(openid);
                Article article = new Article();
                article.setDescription("欢迎来到李睿的个人博客：菜鸟程序员成长之路！"); //图文消息的描述
                article.setPicUrl(userinfo.get("headimgurl")); //图文消息图片地址
                article.setTitle("尊敬的：" + userinfo.get("nickname") + ",你好！");  //图文消息标题
                article.setUrl("earyant.github.io");  //图文url链接
                List<Article> list = new ArrayList<Article>();
                list.add(article);     //这里发送的是单图文，如果需要发送多图文则在这里list中加入多个Article即可！
                newmsg.setArticleCount(list.size());
                newmsg.setArticles(list);
                return MessageUtil.newsMessageToXml(newmsg);
            } catch (Exception e) {
//				System.out.println("====代码有问题额☺！");
                logger.error(e, e);
            }

        }

        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) { // 取消关注事件
//			System.out.println("==============这是取消关注事件！");
        }

        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_SCAN)) { // 扫描二维码事件
//			System.out.println("==============这是扫描二维码事件！");
            Article article = new Article();
            article.setDescription("好吧，既然你扫我了，那我得回应你啊"); //图文消息的描述
            article.setPicUrl("http://res.earyant.com/2016/03/201603086749_6850.png"); //图文消息图片地址
            article.setTitle("扫我干嘛");  //图文消息标题
            article.setUrl("https://earyant.github.io/");  //图文url链接
            List<Article> list = new ArrayList<Article>();
            list.add(article);     //这里发送的是单图文，如果需要发送多图文则在这里list中加入多个Article即可！
            newmsg.setArticleCount(list.size());
            newmsg.setArticles(list);
            return MessageUtil.newsMessageToXml(newmsg);
        }

        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_LOCATION)) { // 位置上报事件
//			System.out.println("==============这是位置上报事件！");
        }

        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_CLICK)) { // 自定义菜单点击事件
//			System.out.println("==============这是自定义菜单点击事件！");
            if (map.get("EventKey").equals("image")) {
                Image img = new Image();
                HttpPostUploadUtil util = new HttpPostUploadUtil();
                HashMap<String, String> userinfo = GetUseInfo.Openid_userinfo(openid);
                String filepath = userinfo.get("headimgurl");
                Map<String, String> textMap = new HashMap<String, String>();
                textMap.put("name", "testname");
                Map<String, String> fileMap = new HashMap<String, String>();
                fileMap.put("userfile", filepath);
                String mediaidrs = util.formUpload(textMap, fileMap);
//                System.out.println(mediaidrs);
                String mediaid = JSONObject.fromObject(mediaidrs).getString("media_id");
                img.setMediaId(mediaid);
                imgmsg.setImage(img);
                return MessageUtil.imageMessageToXml(imgmsg);
            } else if (map.get("EventKey").equals("text")) {
//			    System.out.println("这里是回复图文消息!");
                Article article = new Article();
                article.setDescription("这是图文消息1"); //图文消息的描述
                article.setPicUrl("http://res.earyant.com/2016/03/201603086749_6850.png"); //图文消息图片地址
                article.setTitle("图文消息1");  //图文消息标题
                article.setUrl("https://earyant.github.io/");  //图文url链接
                List<Article> list = new ArrayList<Article>();
                list.add(article);     //这里发送的是单图文，如果需要发送多图文则在这里list中加入多个Article即可！
                newmsg.setArticleCount(list.size());
                newmsg.setArticles(list);
                return MessageUtil.newsMessageToXml(newmsg);
            }
        }

        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_VIEW)) { // 自定义菜单View事件
//			System.out.println("==============这是自定义菜单View事件！");
        }

        return null;
    }
}
