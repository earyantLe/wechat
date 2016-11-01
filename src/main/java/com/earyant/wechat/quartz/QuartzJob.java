package com.earyant.wechat.quartz;

import org.apache.log4j.Logger;

import com.earyant.wechat.common.WeChatTask;

public class QuartzJob {
    private static Logger logger = Logger.getLogger(QuartzJob.class);

    /**
     * @param
     * @Description: 任务执行获取token
     * @author dapengniao
     * @date 2016年3月10日 下午4:34:26
     */
    public void workForToken() {
        try {
            WeChatTask timer = new WeChatTask();
            timer.getToken_getTicket();
        } catch (Exception e) {
            logger.error(e, e);
        }
    }


}
